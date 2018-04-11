package info.wiwitadityasaputra.moviereview;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	private MovieRepository movieRepo;
	@Autowired
	private UserHelper userHelper;

	@RequestMapping(method = RequestMethod.GET, value = "/{movieId}/me")
	public MovieReviewResp getOwnReview(@PathVariable("movieId") int movieId) {
		logger.info("GET " + ApiPath.API_MOVIEREVIEW + "/" + movieId + "/me");
		userHelper.mustHasLoggedUser();

		Movie movie = movieRepo.findOne(movieId);
		User loggedUser = userHelper.getLoggedUser();

		MovieReview mr = movieReviewRepo.findByUserAndMovieAndLatest(loggedUser, movie, true);
		return MovieReviewResp.toOutput(mr);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<MovieReviewResp> getMovieReview(@RequestParam(value = "offset", required = true) int offset,
			@RequestParam(value = "movieId", required = true) int movieId) throws InterruptedException {
		logger.info("GET " + ApiPath.API_MOVIEREVIEW + "?offset=" + offset + "&movieId=" + movieId);

		List<MovieReview> list = movieReviewRepo.findByMovieId(movieId, MOVIEREVIEW_LIMIT, offset);
		List<MovieReviewResp> result = new ArrayList<MovieReviewResp>();
		for (MovieReview mr : list) {
			result.add(MovieReviewResp.toOutput(mr));
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void createMovieReview(@RequestBody MovieReviewReq input) {
		logger.info("POST " + ApiPath.API_MOVIEREVIEW);
		logger.info("movieId = " + input.getMovieId());
		logger.info("review = " + input.getReview());

		userHelper.mustHasLoggedUser();

		User loggedUser = userHelper.getLoggedUser();
		Movie movie = movieRepo.findOne(input.getMovieId());
		MovieReview newMR = new MovieReview();

		MovieReview mr = movieReviewRepo.findByUserAndMovieAndLatest(loggedUser, movie, true);
		if (mr != null) {
			mr.setLatest(false);
			movieReviewRepo.save(mr);
		}

		newMR.setPoint(mr != null ? mr.getPoint() : 0);
		newMR.setLatest(true);
		newMR.setReview(input.getReview());
		newMR.setUser(loggedUser);
		newMR.setMovie(movie);
		movieReviewRepo.save(mr);

	}
}
