package info.wiwitadityasaputra.moviefavorite;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.user.api.UserHelper;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieFavoriteCtrl.class, secure = false)
public class MovieFavoriteCtrlTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private MovieFavoriteRepository movieFavRepo;
	@MockBean
	private MovieRepository movieRepo;
	@MockBean
	private UserHelper userHelper;
	
	@Test
	public void getMovieFavorites_hasLoggedUser_200() throws Exception {
		given(userHelper.getLoggedUser()).willReturn(new User());
		given(movieFavRepo.findByUserAndFavorite(any(User.class), anyBoolean())).willReturn(new ArrayList<MovieFavorite>());
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
			perform(
				get(ApiPath.API_MOVIEFAVORITE).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andReturn().
			getResponse().
			getStatus());
	}
	
	@Test
	public void postMovieFavorite_hasLoggedUser_200() throws Exception {
		given(userHelper.getLoggedUser()).willReturn(new User());
		given(movieRepo.findOne(anyInt())).willReturn(new Movie());
		given(movieFavRepo.findByUserAndMovie(any(User.class), any(Movie.class))).willReturn(new MovieFavorite());
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
			perform(
				post(ApiPath.API_MOVIEFAVORITE).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(new MovieFavoriteReq()))
			).
			andReturn().
			getResponse().
			getStatus());
		
		given(movieFavRepo.findByUserAndMovie(any(User.class), any(Movie.class))).willReturn(null);
		assertEquals(HttpStatus.OK.value(), mockMvc.
				perform(
					post(ApiPath.API_MOVIEFAVORITE).
					contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
					content(new ObjectMapper().writeValueAsString(new MovieFavoriteReq()))
				).
				andReturn().
				getResponse().
				getStatus());
	}
	
	@Test
	public void postMovieFavorite_wrongMovieId_404() throws Exception {
		given(userHelper.getLoggedUser()).willReturn(new User());
		given(movieRepo.findOne(anyInt())).willReturn(null);
		given(movieFavRepo.findByUserAndMovie(any(User.class), any(Movie.class))).willReturn(new MovieFavorite());
		
		assertEquals(HttpStatus.NOT_FOUND.value(), mockMvc.
			perform(
				post(ApiPath.API_MOVIEFAVORITE).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(new MovieFavoriteReq()))
			).
			andReturn().
			getResponse().
			getStatus());
	}
}
