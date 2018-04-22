package info.wiwitadityasaputra.movie.api;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.movie.MovieIdsReq;
import info.wiwitadityasaputra.movie.MovieService;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieByMovieIdsCtrl.class, secure = false)
public class MovieByMovieIdsCtrlTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	private MovieService movieService;

	@Test
	public void getMoviesByIds_validIdsGiven_return200() throws Exception {
		MovieIdsReq input = new MovieIdsReq();
		input.setMovieIds(new int[] { 1, 2, 3 });
		JSONArray movieIds = new JSONArray();
		movieIds.put(1);
		movieIds.put(2);
		movieIds.put(3);
		List<Movie> movies = new ArrayList<Movie>();

		given(movieService.findMovieByIds(movieIds)).willReturn(movies);
		MvcResult response = mockMvc
				.perform(post(ApiPath.API_MOVIE_BYMOVIEIDS).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.content(new ObjectMapper().writeValueAsString(input)))
				.andReturn();

		assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
	}
}
