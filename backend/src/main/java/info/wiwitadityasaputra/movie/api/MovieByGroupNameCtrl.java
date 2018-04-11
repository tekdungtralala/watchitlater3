package info.wiwitadityasaputra.movie.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;
import info.wiwitadityasaputra.util.UpdateMovieSchedule;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

@RestController
@RequestMapping(value = ApiPath.API_MOVIE_BYGROUPNAME)
public class MovieByGroupNameCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(MovieByGroupNameCtrl.class);

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieGroupRepository movieGroupRepo;
	@Autowired
	private MovieSearchRepository movieSearchRepo;
	@Autowired
	private UpdateMovieSchedule updateMovieSchedule;

	@Autowired
	private MovieService movieService;

	private String getMovieTitle(Element e) {
		String title = e.html();
		logger.info("title = " + title);
		int ob = title.indexOf('(');
		int cb = title.indexOf(')');
		if (ob >= 0 && cb >= 0 && ob < cb) {
			title = title.split("\\(")[0].trim();
		}
		return title;
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Movie> getMovieByGroupName(@RequestParam(value = "groupName", required = true) String groupName)
			throws IOException, JSONException {
		logger.info("GET " + ApiPath.API_MOVIE_BYGROUPNAME + "?groupName=" + groupName);
		MovieGroup mg = movieGroupRepo.findByName(groupName);
		if (mg == null) {
			List<Movie> result = new ArrayList<Movie>();
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
				String title = getMovieTitle(e);
				MovieSearch ms = movieSearchRepo.findByQuery(title);
				if (ms == null) {
					ms = new MovieSearch();
					ms.setQuery(title);
					movieSearchRepo.save(ms);
				}
				listMovieSearch.add(ms);
			}
			if (listMovieSearch.isEmpty()) {
				return result;
			}

			// Fetch/create Movie by MovieSearch
			updateMovieSchedule.processMovieSearch();

			// Fetch/create MoviePoster by Movie
			updateMovieSchedule.processMoviePoster(false);

			JSONArray arr = new JSONArray();
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
			return movieService.findMovieByIds(movieIds);
		}
	}
}
