package info.wiwitadityasaputra.movieposter;

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
import org.springframework.test.web.servlet.MvcResult;

import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MoviePosterCtrl.class, secure = false)
public class MoviePosterCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	private MoviePosterRepository moviePosterRepo;
	
	@Test
	public void getPosterByImdbId_validImdbid_200() throws Exception {
		// get movie paster from main data
		MoviePoster mp = new MoviePoster();
		mp.setMain(true);
		List<MoviePoster> list = new ArrayList<MoviePoster>();
		list.add(mp);
		
		given(moviePosterRepo.findByImdbId(anyString())).willReturn(list);
		callGetJPosterByImdbId(HttpStatus.OK.value());
		
		// get movie paster from first data
		list = new ArrayList<MoviePoster>();
		list.add(new MoviePoster());
		
		given(moviePosterRepo.findByImdbId(anyString())).willReturn(list);
		callGetJPosterByImdbId(HttpStatus.OK.value());
	}
	
	@Test
	public void getPosterByImdbId_validImdbid_400() throws Exception {
		given(moviePosterRepo.findByImdbId(anyString())).willReturn(null);
		callGetJPosterByImdbId(HttpStatus.NOT_FOUND.value());
	}
	
	public void callGetJPosterByImdbId(int httpStatus) throws Exception {
		MvcResult response = mockMvc.
			perform(
				get(ApiPath.API_MOVIEPOSTER + "/123").
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andReturn();
		assertEquals(httpStatus, response.getResponse().getStatus());
	}
}
