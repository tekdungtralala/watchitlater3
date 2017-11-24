package info.wiwitadityasaputra.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

	private static String DEFAULT_IMAGE = "java-assets/img_not_found.jpg";
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
	// 3600000 = 60 minute
	@Scheduled(fixedRateString = "3600000", initialDelay = 1000)
	public void start() throws Exception {
		if (!stillRunnig && false) {
			stillRunnig = true;
			logger.info("start(), stillRunnig: " + stillRunnig);

			processMovieSearch();
			processMoviePoster(true);
			updateTop100Movies();
			updateRatingMovie();

			logger.info(" end");
			stillRunnig = false;
		}
	}

	public void updateRatingMovie() {
		logger.info(" update rating movie");
		List<Movie> listMovie = movieRepo.findEmptyRatingMovies();
		logger.info("  listMovie.size() = " + listMovie.size());
		for (Movie movie : listMovie) {
			try {
				String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&i=" + movie.getImdbId() + "&plot=full";
				logger.info("url: " + url);

				RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
				String response = restTemplate.getForObject(url, String.class);
				JSONObject json = new JSONObject(response);
				logger.info(response);

				try {
					movie.setImdbRating(json.getDouble("imdbRating"));
					movieRepo.save(movie);
					logger.info("   success update rating");
				} catch (Exception e) {
					logger.error("  failed update rating, " + e.getMessage());
				}

			} catch (Exception e) {
				logger.error("  failed update rating, " + e.getMessage());
			}
		}
	}

	public void updateTop100Movies() {
		logger.info(" update top 100");
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
	}

	public void processMoviePoster(boolean updateSecondaryImg) throws IOException {
		logger.info(" iterate MoviePoster to find Movie by movie_id");
		for (MoviePoster mp : moviePosterRepo.findAll()) {
			Movie movie = movieRepo.findByImdbId(mp.getImdbId());
			if (movie != null) {
				mp.setMovie(movie);
				moviePosterRepo.save(mp);
			}
		}

		logger.info(" iterate Movie to find empty MoviePoster");
		for (Movie movie : movieRepo.findAll()) {
			List<MoviePoster> list = moviePosterRepo.findByMovie(movie);
			boolean emptyPoster = list == null || list.size() == 0;
			boolean secondaryPoster = list != null && list.size() == 1 && !list.get(0).isMain();
			if (emptyPoster || (secondaryPoster && updateSecondaryImg)) {
				MoviePoster mp = new MoviePoster();
				mp.setMovie(movie);
				mp.setImdbId(movie.getImdbId());

				try {
					logger.info("fetch poster movie: " + movie.getImdbId());

					String url = "http://img.omdbapi.com/?apikey=" + apiKey + "&i=" + movie.getImdbId();
					logger.info("url: " + url);

					RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
					ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
					MediaType contentType = response.getHeaders().getContentType();

					mp.setImgByte(response.getBody());
					mp.setFileType(contentType.getType() + "/" + contentType.getSubtype());
					mp.setMain(true);
					moviePosterRepo.save(mp);
				} catch (Exception e) {

					if (!secondaryPoster) {
						Resource resource = new ClassPathResource(DEFAULT_IMAGE);
						mp.setImgByte(IOUtils.toByteArray(resource.getInputStream()));
						mp.setFileType(MediaType.IMAGE_JPEG_VALUE);
						mp.setMain(false);
						moviePosterRepo.save(mp);
					}

					logger.error(movie.getImdbId() + ", " + e.getMessage());
				}

			}
		}
	}

	public void processMovieSearch() {
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

				boolean hasResponseField = response.indexOf("Response") > -1;
				boolean hasFalseValue = response.indexOf("False") > -1;

				if (hasResponseField && hasFalseValue) {
					logger.info("not found");
					ms.setNotFound(true);
					movieSearchRepo.save(ms);
					continue;
				}

				String imdbId = json.getString("imdbID");
				Movie findedMovie = movieRepo.findByImdbId(imdbId);
				if (findedMovie != null) {
					logger.info("finded movie");
					ms.setMovie(findedMovie);
					ms.setImdbId(imdbId);
					movieSearchRepo.save(ms);
					continue;
				}

				logger.info("create/save new movie");
				Movie movie = new Movie();
				movie.setTitle(json.getString("Title"));
				movie.setImdbId(imdbId);

				try {
					movie.setImdbRating(json.getDouble("imdbRating"));
				} catch (Exception e) {
					movie.setImdbRating(0.0);
				}

				try {
					movie.setReleased(MOVIE_RELEASED_FORMAT.parse(json.getString("Released")));
				} catch (Exception e) {
					movie.setReleased(new Date());
				}

				try {
					movie.setGenre(json.getString("Genre"));
				} catch (Exception e) {
					movie.setGenre("");
				}

				try {
					movie.setYear(json.getInt("Year"));
				} catch (Exception e) {
					movie.setYear(2017);
				}

				try {
					movie.setPlot(json.getString("Plot"));
				} catch (Exception e) {
					movie.setPlot("");
				}

				movie.setJson(response);
				movieRepo.save(movie);

				ms.setMovie(movie);
				ms.setImdbId(movie.getImdbId());
				movieSearchRepo.save(ms);
			} catch (Exception e) {
				logger.error(ms.getQuery() + ", " + e.getMessage());
			}
		}
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
}
