package info.wiwitadityasaputra.movie.api;

import java.util.List;

import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieIdsReq;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_MOVIE_BYMOVIEIDS)
public class MovieByMovieIdsCtrl extends AbstractCtrl {

	@Autowired
	private MovieService movieService;

	@RequestMapping(method = RequestMethod.POST)
	public List<Movie> getMovieByIds(@RequestBody @Valid MovieIdsReq data) throws JSONException {
		JSONArray movieIds = new JSONArray();
		for (int movieId : data.getMovieIds()) {
			movieIds.put(movieId);
		}
		return movieService.findMovieByIds(movieIds);
	}
}
