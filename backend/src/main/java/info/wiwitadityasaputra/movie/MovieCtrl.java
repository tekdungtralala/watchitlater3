package info.wiwitadityasaputra.movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.BorderUIResource.TitledBorderUIResource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.movieposter.MovieGroupName;
import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;
import info.wiwitadityasaputra.util.UpdateMovieSchedule;
import info.wiwitadityasaputra.util.api.AbstractCtrl;

@RestController
@RequestMapping(value = AbstractCtrl.API_PATH_MOVIE)
public class MovieCtrl extends AbstractCtrl {

	private final static String PATH_RANDOM_SIX_MOVIES = "/random-nine-movies";
	private final static String PATH_TOP100_MOVIES = "/top-100-movies";
	private final static String PATH_BY_GROUP_NAME = "/by-group-name";

	private Logger logger = LogManager.getLogger(MovieCtrl.class);

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieGroupRepository movieGroupRepo;
	@Autowired
	private MovieSearchRepository movieSearchRepo;
	@Autowired
	private UpdateMovieSchedule updateMovieSchedule;

	@RequestMapping(method = RequestMethod.GET, value = PATH_BY_GROUP_NAME)
	public List<Movie> getMovieByGroupName(@RequestParam(value = "groupName", required = true) String groupName)
			throws Exception {
		logger.info("GET " + AbstractCtrl.API_PATH_MOVIE + PATH_BY_GROUP_NAME + "?groupName=" + groupName);
		MovieGroup mg = movieGroupRepo.findByName(groupName);
		if (mg == null) {
			String fdow = groupName.split("_")[0];

			// Get movies title
			String url = "http://www.boxofficemojo.com/daily/chart/?sortdate=" + fdow;
			logger.info("url: " + url);
			Document doc = Jsoup.connect(url).timeout(5000).get();
			Elements titles = doc.select("center table tbody tr td table tbody tr td b a");
			logger.info(" titles.size() = " + titles.size());

			// Create MovieSearch
			List<MovieSearch> listMovieSearch = new ArrayList<MovieSearch>();
			for (Element e : titles) {
				String title = e.html();
				logger.info("title = " + title);
				int ob = title.indexOf("(");
				int cb = title.indexOf(")");
				if (ob >= 0 && cb >= 0 && ob < cb) {
					title = title.split("\\(")[0].trim();
				}

				MovieSearch ms = movieSearchRepo.findByQuery(title);
				if (ms == null) {
					ms = new MovieSearch();
					ms.setQuery(title);
					movieSearchRepo.save(ms);
				}
				listMovieSearch.add(ms);
			}

			// Fetch/create Movie by MovieSearch
			updateMovieSchedule.processMovieSearch();

			// Fetch/create MoviePoster by Movie
			updateMovieSchedule.processMoviePoster(false);

			JSONArray arr = new JSONArray();
			List<Movie> result = new ArrayList<Movie>();
			for (MovieSearch ms : listMovieSearch) {
				ms = movieSearchRepo.findOne(ms.getId());
				Movie movie = movieRepo.findByImdbId(ms.getImdbId());
				if (movie != null) {
					result.add(movie);
					arr.put(movie.getId());
				}
			}

			MovieGroup newMovieGroup = new MovieGroup();
			newMovieGroup.setName(groupName);
			newMovieGroup.setMovieIds(arr.toString());
			movieGroupRepo.save(newMovieGroup);

			return result;
		} else {
			JSONArray movieIds = new JSONArray(mg.getMovieIds());
			return findMovieByIds(movieIds);
		}
	}

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
		MovieGroup mg = movieGroupRepo.findByName(MovieGroupName.TOP_100.toString());
		JSONArray movieIds = new JSONArray(mg.getMovieIds());
		return findMovieByIds(movieIds);
	}

	private List<Movie> findMovieByIds(JSONArray movieIds) throws JSONException {
		List<Movie> results = new ArrayList<Movie>();

		for (int i = 0; i < movieIds.length(); i++) {
			int movieId = (Integer) movieIds.get(i);
			results.add(movieRepo.findOne(movieId));
		}

		return results;
	}
}
