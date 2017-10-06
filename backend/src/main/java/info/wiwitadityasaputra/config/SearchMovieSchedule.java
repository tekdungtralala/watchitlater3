package info.wiwitadityasaputra.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;

import info.wiwitadityasaputra.entity.movie.Movie;
import info.wiwitadityasaputra.entity.movie.MovieRepository;
import info.wiwitadityasaputra.entity.moviesearch.MovieSearch;
import info.wiwitadityasaputra.entity.moviesearch.MovieSearchRepository;

@Component
public class SearchMovieSchedule {

	private Logger logger = LogManager.getLogger(SearchMovieSchedule.class);

	private static final DateFormat MOVIE_RELEASED_FORMAT = new SimpleDateFormat("dd MMM yyyy");
	private boolean stillRunnig = false;

	@Value("${com.omdbapi.apikey}")
	protected String apiKey;

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieSearchRepository movieSearchRepo;

	// 10000 = 10 second
	// 900000 = 15 minute
	@Scheduled(fixedRateString = "900000", initialDelay = 1000)
	public void start() throws Exception {
		if (!stillRunnig) {
			stillRunnig = true;
			logger.info("start(), stillRunnig: " + stillRunnig);

			List<MovieSearch> list = movieSearchRepo.findByEmptyMovie();
			logger.info(" list.length: " + list.size());

			for (MovieSearch ms : list) {

				try {
					logger.info(ms.getId() + " - " + ms.getQuery());

					ms.setQuery(ms.getQuery().trim());

					String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + ms.getQuery().trim();
					logger.info("url: " + url);

					RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
					String response = restTemplate.getForObject(url, String.class);
					JSONObject json = new JSONObject(response);
					logger.info(response);

					Movie movie = new Movie();
					movie.setTitle(json.getString("Title"));
					movie.setImdbRating(json.getDouble("imdbRating"));
					movie.setReleased(MOVIE_RELEASED_FORMAT.parse(json.getString("Released")));
					movie.setGenre(json.getString("Genre"));
					movie.setYear(json.getInt("Year"));
					movie.setPlot(json.getString("Plot"));
					movie.setJson(response);
					movieRepo.save(movie);

					ms.setMovie(movie);
					movieSearchRepo.save(ms);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}

			stillRunnig = false;
		}
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 50000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
}
