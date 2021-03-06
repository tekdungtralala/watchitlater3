package info.wiwitadityasaputra.moviefavorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.user.api.UserHelper;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.exception.NotFoundException;

@RestController
@RequestMapping(value = ApiPath.API_MOVIEFAVORITE)
public class MovieFavoriteCtrl extends AbstractCtrl {

	@Autowired
	private MovieFavoriteRepository movieFavRepo;
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private UserHelper userHelper;

	@RequestMapping(method = RequestMethod.GET)
	public List<MovieFavorite> getMovieFavorites() {
		userHelper.mustHasLoggedUser();
		User user = userHelper.getLoggedUser();
		return movieFavRepo.findByUserAndFavorite(user, true);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void postMovieFavorite(@RequestBody MovieFavoriteReq data) {
		userHelper.mustHasLoggedUser();
		User user = userHelper.getLoggedUser();
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
	}
}
