package info.wiwitadityasaputra.user.api;

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

import info.wiwitadityasaputra.util.api.HomeCtrl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HomeCtrl.class, secure = false)
public class HomeCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@Test
	public void getIndex_200() throws Exception {
		mockMvc.
			perform(
				get("/").
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andExpect(status().isOk());
	}
}
