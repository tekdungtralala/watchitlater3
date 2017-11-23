package info.wiwitadityasaputra.moviereview;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.user.api.UserHelper;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_MOVIEREVIEW)
public class MovieReviewCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MovieReviewCtrl.class);

	private static int MOVIEREVIEW_LIMIT = 3;

	@Autowired
	private MovieReviewRepository movieReviewRepo;
	@Autowired
	private UserHelper userHelper;
	@Autowired
	private MovieRepository movieRepo;

	@RequestMapping(method = RequestMethod.GET)
	public List<MovieReviewOutput> getMovieReview(@RequestParam(value = "offset", required = true) int offset,
			@RequestParam(value = "movieId", required = true) int movieId) {
		logger.info("GET " + ApiPath.API_MOVIEREVIEW + "?offset=" + offset + "&movieId=" + movieId);

		List<MovieReview> list = movieReviewRepo.findByMovieId(movieId, MOVIEREVIEW_LIMIT, offset);
		List<MovieReviewOutput> result = new ArrayList<MovieReviewOutput>();
		for (MovieReview mr : list) {
			result.add(MovieReviewOutput.toOutput(mr));
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void createMovieReview(@RequestBody MovieReviewInput input) {
		logger.info("POST " + ApiPath.API_MOVIEREVIEW);
		logger.info(" movieId = " + input.getMovieid());
		logger.info(" review = " + input.getReview());

		userHelper.mustHasLoggedUser();

		User loggedUser = userHelper.getLoggedUser();
		Movie movie = movieRepo.findOne(input.getMovieid());

		MovieReview mr = movieReviewRepo.findByUserAndMovie(loggedUser, movie);
		if (mr != null) {
			mr.setLatest(false);
			movieReviewRepo.save(mr);
		}

		mr = new MovieReview();
		mr.setLatest(true);
		mr.setPoint(0);
		mr.setReview(input.getReview());
		mr.setUser(loggedUser);
		mr.setMovie(movie);
		movieReviewRepo.save(mr);

	}
}
