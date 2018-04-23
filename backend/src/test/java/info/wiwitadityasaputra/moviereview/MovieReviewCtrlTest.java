package info.wiwitadityasaputra.moviereview;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieRepository;
import info.wiwitadityasaputra.user.api.UserHelper;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieReviewCtrl.class, secure = false)
public class MovieReviewCtrlTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private MovieReviewRepository movieReviewRepo;
	@MockBean
	private MovieRepository movieRepo;
	@MockBean
	private UserHelper userHelper;
	
	@Test
	public void getOwnReview_hasLoggedUser_200() throws Exception {
		given(movieRepo.findOne(anyInt())).willReturn(new Movie());
		given(userHelper.getLoggedUser()).willReturn(new User());
		given(movieReviewRepo.findByUserAndMovieAndLatest(any(User.class), any(Movie.class), anyBoolean())).willReturn(null);
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
			perform(
				get(ApiPath.API_MOVIEREVIEW + "/123/me").
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andReturn().
			getResponse().
			getStatus());
	}
	
	@Test
	public void getMovieReview_hasLoggedUser_200() throws Exception {
		List<MovieReview> list = new ArrayList<MovieReview>();
		list.add(getDummyMovieReview());
		given(movieReviewRepo.findByMovieId(anyInt(), anyInt(), anyInt())).willReturn(list);
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
			perform(
				get(ApiPath.API_MOVIEREVIEW).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				param("offset", "1").
				param("movieId", "1")
			).
			andReturn().
			getResponse().
			getStatus());
	}
	
	@Test
	public void createMovieReview_200() throws Exception {
		given(movieRepo.findOne(anyInt())).willReturn(new Movie());
		given(userHelper.getLoggedUser()).willReturn(new User());
		List<MovieReview> list = new ArrayList<MovieReview>();
		list.add(getDummyMovieReview());
		given(movieReviewRepo.findByMovieId(anyInt(), anyInt(), anyInt())).willReturn(list);
		
		postMovieReview();
		
		MovieReview mr = new MovieReview();
		mr.setPoint(1);
		given(movieReviewRepo.findByUserAndMovieAndLatest(any(User.class), any(Movie.class), anyBoolean())).willReturn(mr);
		
		postMovieReview();
	}
	
	private void postMovieReview() throws JsonProcessingException, Exception {
		MovieReviewReq input = new MovieReviewReq();
		input.setReview("abc");
		
		assertEquals(HttpStatus.OK.value(), mockMvc.
			perform(
				post(ApiPath.API_MOVIEREVIEW).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(input))
			).
			andReturn().
			getResponse().
			getStatus());
		
	}

	private MovieReview getDummyMovieReview() {
		MovieReview mr = new MovieReview();
		mr.setId(1);
		mr.setReview("abc");
		Movie m = new Movie();
		m.setId(1);
		m.setTitle("abc");
		mr.setMovie(m);
		User u = new User();
		u.setUserId("abc");
		mr.setUser(u);
		return mr;
	}
}
