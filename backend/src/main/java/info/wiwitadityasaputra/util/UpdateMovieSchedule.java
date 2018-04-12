package info.wiwitadityasaputra.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
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
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UpdateMovieSchedule {

	private static final String DEFAULT_IMAGE = "java-assets/img_not_found.jpg";
	private final DateFormat movieReleaseFormat = new SimpleDateFormat("dd MMM yyyy");
	private boolean stillRunnig = false;

	@Value("${com.omdbapi.apikey}")
	protected String apiKey;

	@Value("${app.config.runUpdateMovie}")
	protected boolean runUpdateMovie;

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
	public void start() throws IOException {
		log.info("start(), stillRunnig: {}, runUpdateMovie: {}", stillRunnig, runUpdateMovie);
		if (!stillRunnig && runUpdateMovie) {
			stillRunnig = true;

			processMovieSearch();
			processMoviePoster(true);
			updateTop100Movies();
			updateRatingMovie();

			log.info(" end");
			stillRunnig = false;
		}
	}

	public void updateRatingMovie() {
		log.info(" update rating movie");
		List<Movie> listMovie = movieRepo.findEmptyRatingMovies();
		log.info("  listMovie.size() = {}", listMovie.size());
		for (Movie movie : listMovie) {
			try {
				String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&i=" + movie.getImdbId() + "&plot=full";
				log.info("url: {}", url);

				RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
				String response = restTemplate.getForObject(url, String.class);
				JSONObject json = new JSONObject(response);
				log.info(response);

				saveMovie(movie, json);
			} catch (Exception e) {
				log.error("  failed update rating, " + e.getMessage());
			}
		}
	}

	public void saveMovie(Movie movie, JSONObject json) {
		try {
			movie.setImdbRating(json.getDouble("imdbRating"));
			movieRepo.save(movie);
			log.info("   success update rating");
		} catch (Exception e) {
			log.error("  failed update rating {} ",  e.getMessage(), e);
		}
	}

	public void updateTop100Movies() {
		log.info(" update top 100");
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

	private void fillEmptyMoviePoster() {
		log.info(" iterate MoviePoster to find Movie by movie_id");
		for (MoviePoster mp : moviePosterRepo.findAll()) {
			Movie movie = movieRepo.findByImdbId(mp.getImdbId());
			if (movie != null) {
				mp.setMovie(movie);
				moviePosterRepo.save(mp);
			}
		}
	}

	public void processMoviePoster(boolean updateSecondaryImg) throws IOException {
		fillEmptyMoviePoster();

		log.info(" iterate Movie to find empty MoviePoster");
		for (Movie movie : movieRepo.findAll()) {
			List<MoviePoster> list = moviePosterRepo.findByMovie(movie);
			boolean emptyPoster = list == null || list.isEmpty();
			boolean secondaryPoster = list != null && list.size() == 1 && !list.get(0).isMain();
			if (emptyPoster || (secondaryPoster && updateSecondaryImg)) {
				MoviePoster mp = new MoviePoster();
				mp.setMovie(movie);
				mp.setImdbId(movie.getImdbId());

				try {
					log.info("fetch poster movie: " + movie.getImdbId());

					String url = "http://img.omdbapi.com/?apikey=" + apiKey + "&i=" + movie.getImdbId();
					log.info("url: " + url);

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

					log.error(movie.getImdbId() + ", " + e.getMessage());
				}

			}
		}
	}

	public void processMovieSearch() {
		List<MovieSearch> listMovieSearch = movieSearchRepo.findByEmptyMovie();
		log.info(" listMovieSearch.length: " + listMovieSearch.size());
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
				log.info(ms.getId() + " - " + ms.getQuery());

				ms.setQuery(ms.getQuery().trim());

				String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + ms.getQuery().trim();
				log.info("search movie url {}", url);

				RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
				String response = restTemplate.getForObject(url, String.class);
				JSONObject json = new JSONObject(response);
				log.info(response);

				boolean hasResponseField = response.indexOf("Response") > -1;
				boolean hasFalseValue = response.indexOf("False") > -1;

				if (hasResponseField && hasFalseValue) {
					log.info("not found");
					ms.setNotFound(true);
					movieSearchRepo.save(ms);
				} else {
					String imdbId = json.getString("imdbID");
					Movie findedMovie = movieRepo.findByImdbId(imdbId);
					if (findedMovie != null) {
						log.info("finded movie");
						ms.setMovie(findedMovie);
						ms.setImdbId(imdbId);
						movieSearchRepo.save(ms);
					} else {
						log.info("create/save new movie");
						Movie movie = new Movie();
						movie.setTitle(getStringValue(json, "Title"));
						movie.setImdbId(imdbId);
						movie.setImdbRating(getDoubleValue(json, "imdbRating", 0.0));
						movie.setReleased(getDateValue(json, "Released", new Date()));
						movie.setGenre(getStringValue(json, "Genre"));
						movie.setYear(getIntValue(json, "Year", 2017));
						movie.setPlot(getStringValue(json, "Plot"));

						movie.setJson(response);
						movieRepo.save(movie);

						ms.setMovie(movie);
						ms.setImdbId(movie.getImdbId());
						movieSearchRepo.save(ms);
					}
				}

			} catch (Exception e) {
				log.error(ms.getQuery() + ", " + e.getMessage());
			}
		}
	}

	private Date getDateValue(JSONObject json, String key, Date defaultValue) {
		try {
			return movieReleaseFormat.parse(json.getString(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private double getDoubleValue(JSONObject json, String key, double defaultValue) {
		try {
			return json.getDouble(key);
		} catch (JSONException e) {
			return defaultValue;
		}
	}

	private int getIntValue(JSONObject json, String key, int defaultValue) {
		try {
			return json.getInt(key);
		} catch (JSONException e) {
			return defaultValue;
		}
	}

	private String getStringValue(JSONObject json, String key) {
		try {
			return json.getString(key);
		} catch (JSONException e) {
			return "";
		}
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
}
