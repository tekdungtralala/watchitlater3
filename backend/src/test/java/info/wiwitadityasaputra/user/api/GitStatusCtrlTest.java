package info.wiwitadityasaputra.user.api;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.GitStatusCtrl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GitStatusCtrl.class, secure = false)
public class GitStatusCtrlTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Test
	public void getGitStatus_200() throws Exception {
		assertEquals(HttpStatus.OK.value(), mockMvc.
			perform(
				get(ApiPath.API_GITSTATUS).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andReturn().
			getResponse().
			getStatus());	
	}
}
