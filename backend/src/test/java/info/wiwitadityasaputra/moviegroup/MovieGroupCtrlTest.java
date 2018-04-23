package info.wiwitadityasaputra.moviegroup;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
@WebMvcTest(value = MovieGroupCtrl.class, secure = false)
public class MovieGroupCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	private MovieGroupRepository movieGroupRepo;
	
	@Test
	public void getMovieGroup_inAllDay_200() throws Exception {
		callGetMovieGroup("2018-4-15");
		callGetMovieGroup("2018-4-16");
		callGetMovieGroup("2018-4-17");
		callGetMovieGroup("2018-4-18");
		callGetMovieGroup("2018-4-19");
		callGetMovieGroup("2018-4-20");
		callGetMovieGroup("2018-4-21");
	}
	
	public void callGetMovieGroup(String dateStr) throws Exception {
		MvcResult response = mockMvc.
				perform(
					get(ApiPath.API_MOVIEGROUP).
					contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
					param("date", dateStr)
				).
				andReturn();
		assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());		
	}
}
