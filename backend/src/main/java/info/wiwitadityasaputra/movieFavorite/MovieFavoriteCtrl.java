package info.wiwitadityasaputra.movieFavorite;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.exception.NotFoundException;

@RestController
@RequestMapping(value = ApiPath.API_PATH_MOVIE_FAVORITE)
public class MovieFavoriteCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MovieFavoriteCtrl.class);

	@Autowired
	private MovieFavoriteRepository movieFavRepo;
	@Autowired
	private MovieRepository movieRepo;

	@RequestMapping(method = RequestMethod.GET)
	public List<MovieFavorite> getMovieFavorites() {
		logger.info("GET " + ApiPath.API_PATH_MOVIE_FAVORITE);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<MovieFavorite> result = new ArrayList<MovieFavorite>();
		result = movieFavRepo.findByUserAndFavorite(user, true);
		return result;
	}

	@RequestMapping(method = RequestMethod.POST)
	public void postMovieFavorite(@RequestBody MovieFavoriteInput data) {
		logger.info("POST " + ApiPath.API_PATH_MOVIE_FAVORITE);
		logger.info("  movieId = " + data.getMovieId() + ",  isFavorite = " + data.isFavorite());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Movie movie = movieRepo.findOne(data.getMovieId());
		if (movie == null)
			throw new NotFoundException("Can't find movie with id=" + data.getMovieId());

		MovieFavorite mf = movieFavRepo.findByUserAndMovie(user, movie);
		if (mf == null)
			mf = new MovieFavorite();

		mf.setUser(user);
		mf.setMovie(movie);
		mf.setFavorite(data.isFavorite());
		mf.setPosition(0);
		movieFavRepo.save(mf);
		logger.info("  id = " + mf.getId());
	}
}
