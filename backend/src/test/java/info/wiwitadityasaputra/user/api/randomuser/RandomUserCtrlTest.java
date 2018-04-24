package info.wiwitadityasaputra.user.api.randomuser;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RandomUserCtrl.class, secure = false)
public class RandomUserCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Test
	public void abc() throws Exception {
		mockMvc.
			perform(
				get(ApiPath.API_USER_RANDOM).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andExpect(status().isOk());
	}
}
