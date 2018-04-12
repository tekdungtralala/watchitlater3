package info.wiwitadityasaputra.movie.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.movieposter.MovieGroupName;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_MOVIE)
public class MovieCtrl extends AbstractCtrl {

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieGroupRepository movieGroupRepo;

	@Autowired
	private MovieService movieService;

	@RequestMapping(method = RequestMethod.GET, value = ApiPath.API_MOVIE_RANDOMSIXMOVIES)
	public List<Movie> getRandom9Movies() {
		List<Movie> list = movieRepo.findAll();
		int max = list.size() - 1;

		List<Movie> results = new ArrayList<Movie>();
		for (int i = 0; i < 9; i++) {
			Random random = new Random();
			int r = random.nextInt(max);
			results.add(list.get(r));
			list.remove(r);
			max--;
		}

		return results;
	}

	@RequestMapping(method = RequestMethod.GET, value = ApiPath.API_MOVIE_TOP100MOVIES)
	public List<Movie> getTop100Movies() throws JSONException {
		MovieGroup mg = movieGroupRepo.findByName(MovieGroupName.TOP_100.toString());
		JSONArray movieIds = new JSONArray(mg.getMovieIds());
		return movieService.findMovieByIds(movieIds);
	}
}
