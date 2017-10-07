package info.wiwitadityasaputra.movie;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;

import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.movieposter.MovieGroupName;
import info.wiwitadityasaputra.util.api.AbstractCtrl;

@RestController
@RequestMapping(value = AbstractCtrl.API_PATH_MOVIE)
public class MovieCtrl extends AbstractCtrl {

	private final static String PATH_RANDOM_SIX_MOVIES = "/random-nine-movies";
	private final static String PATH_TOP100_MOVIES = "/top-100-movies";

	private Logger logger = LogManager.getLogger(MovieCtrl.class);

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieGroupRepository movieGroupRepo;

	@RequestMapping(method = RequestMethod.GET, value = PATH_RANDOM_SIX_MOVIES)
	public List<Movie> getRandom9Movies() {
		logger.info("GET " + AbstractCtrl.API_PATH_MOVIE + PATH_RANDOM_SIX_MOVIES);
		List<Movie> list = movieRepo.findAll();
		int max = list.size() - 1;
		int min = 0;

		List<Movie> results = new ArrayList<Movie>();
		for (int i = 0; i < 9; i++) {
			int r = min + (int) (Math.random() * ((max - min) + 1));
			results.add(list.get(r));
			list.remove(r);
			max--;
		}

		return results;
	}

	@RequestMapping(method = RequestMethod.GET, value = PATH_TOP100_MOVIES)
	public List<Movie> getTop100Movies() throws JSONException {
		logger.info("GET " + AbstractCtrl.API_PATH_MOVIE + PATH_TOP100_MOVIES);

		List<Movie> results = new ArrayList<Movie>();

		MovieGroup mg = movieGroupRepo.findByName(MovieGroupName.TOP_100.toString());
		JSONArray movieIds = new JSONArray(mg.getMovieIds());

		for (int i = 0; i < movieIds.length(); i++) {
			int movieId = (Integer) movieIds.get(i);
			results.add(movieRepo.findOne(movieId));
		}

		return results;
	}
}
