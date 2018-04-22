package info.wiwitadityasaputra.movie.api;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
import info.wiwitadityasaputra.movie.MovieHelper;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.moviegroup.MovieGroup;
import info.wiwitadityasaputra.moviegroup.MovieGroupRepository;
import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;
import info.wiwitadityasaputra.util.UpdateMovieSchedule;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieByGroupNameCtrl.class, secure = false)
public class MovieByGroupNameCtrlTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private MovieRepository movieRepo;
	@MockBean
	private MovieGroupRepository movieGroupRepo;
	@MockBean
	private MovieSearchRepository movieSearchRepo;
	@MockBean
	private UpdateMovieSchedule updateMovieSchedule;
	@MockBean
	private MovieService movieService;
	@MockBean
	private MovieHelper movieHelper;

	@Test
	public void getMovieByGroupName_groupNotExistAndEmptyFetching_returnEmptyMovies() throws Exception {
		given(movieGroupRepo.findByName(anyString())).willReturn(null);
		given(movieHelper.fetchMovieByGroupName(anyString())).willReturn(new ArrayList<MovieSearch>());
		
		MvcResult response = mockMvc.
				perform(
					get(ApiPath.API_MOVIE_BYGROUPNAME).
					contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
					param("groupName", "any-string")
				).
				andReturn();
		assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
	}
	
	@Test
	public void getMovieByGroupName_groupNotExistAndNotEmptyFetching_returnMovies() throws Exception {
		given(movieGroupRepo.findByName(anyString())).willReturn(null);
		
		ArrayList<MovieSearch> moviesSearch = new ArrayList<MovieSearch>();
		MovieSearch ms = new MovieSearch();
		ms.setId(1);
		moviesSearch.add(ms);
		given(movieHelper.fetchMovieByGroupName(anyString())).willReturn(moviesSearch);
		
		given(movieSearchRepo.findOne(anyInt())).willReturn(ms);
		given(movieRepo.findByImdbId(anyString())).willReturn(new Movie());
		
		MvcResult response = mockMvc.
				perform(
					get(ApiPath.API_MOVIE_BYGROUPNAME).
					contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
					param("groupName", "any-string")
				).
				andReturn();
		assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
	}

	@Test
	public void getMovieByGroupName_groupExist_returnMovies() throws Exception {
		MovieGroup result = new MovieGroup();
		result.setMovieIds("[1,2,3,4]");

		given(movieGroupRepo.findByName(anyString())).willReturn(result);
		given(movieService.findMovieByIds(new JSONArray(result.getMovieIds()))).willReturn(new ArrayList<Movie>());

		MvcResult response = mockMvc.
			perform(
				get(ApiPath.API_MOVIE_BYGROUPNAME).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				param("groupName", "any-string")
			).
			andReturn();
		assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
	}
}
