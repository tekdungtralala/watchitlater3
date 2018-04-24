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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserProfilePictureCtrl.class, secure = false)
public class UserProfilePictureCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepo;
	
	@Test
	public void getPPByUserId_noUser_returnDefaultImage() throws Exception {
		getPPByUserId();
	}
	
	@Test
	public void getPPByUserId_hasUser_returnUserImage() throws Exception {
		User user = new User();
		user.setFileType(MediaType.IMAGE_PNG_VALUE);
		user.setProfilePicture(String.valueOf("any-string").getBytes());
		given(userRepo.findByUserId(anyString())).willReturn(user);
		getPPByUserId();
	}
	
	private void getPPByUserId() throws Exception {
		mockMvc.
			perform(
				get(ApiPath.API_USER_PROFILEPICTURE + "/123" ).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andExpect(status().isOk());		
	}
}
