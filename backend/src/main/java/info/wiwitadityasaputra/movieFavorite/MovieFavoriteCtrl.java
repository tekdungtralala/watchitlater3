package info.wiwitadityasaputra.movieFavorite;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_PATH_MOVIE_FAVORITE)
public class MovieFavoriteCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MovieFavoriteCtrl.class);

	@Autowired
	private MovieFavoriteRepository movieFavRepo;

	@RequestMapping(method = RequestMethod.GET)
	public List<MovieFavorite> getMovieFavorites() {
		logger.info("GET " + ApiPath.API_PATH_MOVIE_FAVORITE);
		List<MovieFavorite> result = new ArrayList<MovieFavorite>();

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		result = movieFavRepo.findByUser(user);

		return result;
	}
}
