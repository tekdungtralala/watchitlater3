package info.wiwitadityasaputra.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.movieposter.MovieGroupName;
import info.wiwitadityasaputra.movieposter.MoviePoster;
import info.wiwitadityasaputra.movieposter.MoviePosterRepository;
import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;

@Component
public class UpdateMovieSchedule {

	private Logger logger = LogManager.getLogger(UpdateMovieSchedule.class);

	private static final DateFormat MOVIE_RELEASED_FORMAT = new SimpleDateFormat("dd MMM yyyy");
	private boolean stillRunnig = false;

	@Value("${com.omdbapi.apikey}")
	protected String apiKey;

	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private MovieSearchRepository movieSearchRepo;
	@Autowired
	private MoviePosterRepository moviePosterRepo;
	@Autowired
	private MovieGroupRepository movieGroupRepo;

	// 10000 = 10 second
	// 900000 = 15 minute
	@Scheduled(fixedRateString = "900000", initialDelay = 1000)
	public void start() throws Exception {
		if (!stillRunnig) {
			stillRunnig = true;
			logger.info("start(), stillRunnig: " + stillRunnig);

			List<MovieSearch> listMovieSearch = movieSearchRepo.findByEmptyMovie();
			logger.info(" listMovieSearch.length: " + listMovieSearch.size());
			for (MovieSearch ms : listMovieSearch) {
				boolean finded = false;
				if (ms.getImdbId() != null) {
					Movie movie = movieRepo.findByImdbId(ms.getImdbId());

					ms.setMovie(movie);
					movieSearchRepo.save(ms);
				}
				if (finded)
					continue;
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
					movie.setImdbId(json.getString("imdbID"));
					movie.setImdbRating(json.getDouble("imdbRating"));
					movie.setReleased(MOVIE_RELEASED_FORMAT.parse(json.getString("Released")));
					movie.setGenre(json.getString("Genre"));
					movie.setYear(json.getInt("Year"));
					movie.setPlot(json.getString("Plot"));
					movie.setJson(response);
					movieRepo.save(movie);

					ms.setMovie(movie);
					ms.setImdbId(movie.getImdbId());
					movieSearchRepo.save(ms);
				} catch (Exception e) {
					logger.error(ms.getQuery() + ", " + e.getMessage());
				}
			}

			for (MoviePoster mp : moviePosterRepo.findAll()) {
				Movie movie = movieRepo.findByImdbId(mp.getImdbId());
				if (movie != null) {
					mp.setMovie(movie);
					moviePosterRepo.save(mp);
				}
			}

			for (Movie movie : movieRepo.findAll()) {
				List<MoviePoster> list = moviePosterRepo.findByMovie(movie);
				if (list == null || list.size() == 0) {
					try {
						logger.info("fetch poster movie: " + movie.getImdbId());

						String url = "http://img.omdbapi.com/?apikey=" + apiKey + "&i=" + movie.getImdbId();
						logger.info("url: " + url);

						RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
						ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, null,
								byte[].class);
						MediaType contentType = response.getHeaders().getContentType();

						MoviePoster mp = new MoviePoster();
						mp.setMovie(movie);
						mp.setImdbId(movie.getImdbId());
						mp.setImgByte(response.getBody());
						mp.setMain(true);
						mp.setFileType(contentType.getType() + "/" + contentType.getSubtype());
						moviePosterRepo.save(mp);
					} catch (Exception e) {
						logger.error(movie.getImdbId() + ", " + e.getMessage());
					}

				}
			}

			MovieGroup movieGroup = movieGroupRepo.findByName(MovieGroupName.TOP_100.toString());
			if (movieGroup == null) {
				movieGroup = new MovieGroup();
			}
			JSONArray movieIds = new JSONArray();
			for (Movie movie : movieRepo.findTop100Movies()) {
				movieIds.put(movie.getId());
			}
			movieGroup.setName(MovieGroupName.TOP_100.toString());
			movieGroup.setMovieIds(movieIds.toString());
			movieGroupRepo.save(movieGroup);

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
