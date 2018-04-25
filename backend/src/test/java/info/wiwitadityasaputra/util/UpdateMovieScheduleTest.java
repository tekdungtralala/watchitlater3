package info.wiwitadityasaputra.util;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.MultiValueMap;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.movieposter.MoviePoster;
import info.wiwitadityasaputra.movieposter.MoviePosterRepository;
import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;
import info.wiwitadityasaputra.user.api.UserHelper;

@RunWith(MockitoJUnitRunner.class)
public class UpdateMovieScheduleTest {

	@InjectMocks
	private UpdateMovieSchedule updateMovieSchedule;

	@Mock
	private MovieRepository movieRepo;
	@Mock
	private MovieSearchRepository movieSearchRepo;
	@Mock
	private MoviePosterRepository moviePosterRepo;
	@Mock
	private MovieGroupRepository movieGroupRepo;
	@Mock
	private OmdbapiHelper omdbapiHelper;
	
	@Test
	public void start_noError() throws IOException {
		ReflectionTestUtils.setField(updateMovieSchedule, "runUpdateMovie", true);
		updateMovieSchedule.start();
	}

	@Test
	public void updateRatingMovie_noError() throws JSONException {
		List<Movie> listMovie = new ArrayList<Movie>();
		Movie m1 = new Movie();
		m1.setImdbId("tt0111161");
		listMovie.add(m1);
		given(movieRepo.findEmptyRatingMovies()).willReturn(listMovie);
		JSONObject json = new JSONObject();
		json.put("imdbRating", 11d);
		given(omdbapiHelper.getFullMovie(anyString())).willReturn(json);

		updateMovieSchedule.updateRatingMovie();
		
		JSONObject json1 = new JSONObject();
		json1.put("imdbRating", "not-double");
		given(omdbapiHelper.getFullMovie(anyString())).willReturn(json1);

		updateMovieSchedule.updateRatingMovie();

		given(omdbapiHelper.getFullMovie(anyString())).willThrow(new JSONException(""));
		updateMovieSchedule.updateRatingMovie();
	}

	@Test
	public void updateTop100Movies_noError() {
		given(movieGroupRepo.findByName(anyString())).willReturn(null);
		List<Movie> movies = new ArrayList<Movie>();
		Movie m = new Movie();
		m.setId(123);
		movies.add(m);
		given(movieRepo.findTop100Movies()).willReturn(movies);

		updateMovieSchedule.updateTop100Movies();
	}

	@Test
	public void processMoviePoster_noError() throws IOException {
		List<MoviePoster> mps = new ArrayList<MoviePoster>();
		MoviePoster mp1 = new MoviePoster();
		mp1.setImdbId("id");
		mps.add(mp1);

		List<Movie> ms = new ArrayList<Movie>();
		Movie m = new Movie();
		m.setImdbId("id");
		ms.add(m);

		given(moviePosterRepo.findAll()).willReturn(mps);
		given(movieRepo.findByImdbId(anyString())).willReturn(new Movie());
		given(movieRepo.findAll()).willReturn(ms);

		updateMovieSchedule.processMoviePoster(true);

		MultiValueMap<String, String> headers = new HttpHeaders(); 
		headers.set("Content-Type", MediaType.IMAGE_JPEG_VALUE);
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(String.valueOf("").getBytes(), headers, HttpStatus.OK);
		given(omdbapiHelper.getImage(anyString())).willReturn(response);
		updateMovieSchedule.processMoviePoster(true);
	}

	@Test
	public void processMovieSearch_noError() throws JSONException {
		ReflectionTestUtils.setField(updateMovieSchedule, "apiKey", System.getenv("WATCHITLATER3_OMDBAPI_APIKEY"));

		List<MovieSearch> movieSearch = new ArrayList<MovieSearch>();
		MovieSearch ms1 = new MovieSearch();
		ms1.setImdbId("id1");
		ms1.setQuery("batman 1990");
		movieSearch.add(ms1);
		given(movieSearchRepo.findByEmptyMovie()).willReturn(movieSearch);
		given(movieRepo.findByImdbId(ms1.getImdbId())).willReturn(new Movie());
		JSONObject json1 = new JSONObject();
		json1.put("Response", false);
		given(omdbapiHelper.getMovie(anyString())).willReturn(json1);
		updateMovieSchedule.processMovieSearch();
		
		JSONObject json2 = new JSONObject();
		json2.put("Response", true);
		json2.put("imdbID", "imdbID");
		given(omdbapiHelper.getMovie(anyString())).willReturn(json2);
		updateMovieSchedule.processMovieSearch();

		JSONObject json3 = new JSONObject();
		json3.put("Response", true);
		json3.put("imdbID", "imdbID");
		json3.put("Title", "Title");
		json3.put("imdbRating", 11d);
		json3.put("Released", "11 Jan 2011");
		json3.put("Genre", "Genre");
		json3.put("Year", 2011);
		json3.put("Plot", "Plot");
		given(omdbapiHelper.getMovie(anyString())).willReturn(json3);
		updateMovieSchedule.processMovieSearch();
		
		given(movieRepo.findByImdbId(anyString())).willReturn(new Movie());
		updateMovieSchedule.processMovieSearch();
		
		given(omdbapiHelper.getMovie(anyString())).willThrow(new JSONException(""));
		updateMovieSchedule.processMovieSearch();
	}
}
