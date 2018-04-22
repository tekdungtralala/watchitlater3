package info.wiwitadityasaputra.movie.api;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieCtrl.class, secure = false)
public class MovieCtrlTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private MovieRepository movieRepo;
	@MockBean
	private MovieGroupRepository movieGroupRepo;
	@MockBean
	private MovieService movieService;
	
	@Test
	public void getRandom6Movies_returnMovies() throws Exception {
		List<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie());
		movies.add(new Movie());
		given(movieRepo.findAll()).willReturn(movies);
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
				perform(
						get(ApiPath.API_MOVIE + ApiPath.API_MOVIE_RANDOMSIXMOVIES).
						contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				).
				andReturn().
				getResponse().
				getStatus());
	}

	@Test
	public void getTop100Movies_returnMovies() throws Exception {
		MovieGroup mg = new MovieGroup();
		mg.setMovieIds("[1,2,3]");
		given(movieGroupRepo.findByName(anyString())).willReturn(mg);
		given(movieService.findMovieByIds(new JSONArray(mg.getMovieIds()))).willReturn(null);
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
				perform(
						get(ApiPath.API_MOVIE + ApiPath.API_MOVIE_TOP100MOVIES).
						contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				).
				andReturn().
				getResponse().
				getStatus());
	}
}
