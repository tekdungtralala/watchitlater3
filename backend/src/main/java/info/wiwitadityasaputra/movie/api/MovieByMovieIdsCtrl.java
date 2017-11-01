package info.wiwitadityasaputra.movie.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieIdsInput;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_PATH_MOVIE_BY_MOVIEIDS)
public class MovieByMovieIdsCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MovieByMovieIdsCtrl.class);

	@Autowired
	private MovieService movieService;

	@RequestMapping(method = RequestMethod.POST)
	public List<Movie> getMovieByIds(@RequestBody @Valid MovieIdsInput data) throws JSONException {
		logger.info("GET " + ApiPath.API_PATH_MOVIE_BY_MOVIEIDS);
		logger.info("  movieIds size = " + data.getMovieIds().length);

		JSONArray movieIds = new JSONArray();
		for (int movieId : data.getMovieIds()) {
			movieIds.put(movieId);
		}

		List<Movie> result = movieService.findMovieByIds(movieIds);
		logger.info("  movie size = " + result.size());
		return result;
	}
}
