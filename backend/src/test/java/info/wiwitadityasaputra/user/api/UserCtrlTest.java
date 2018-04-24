package info.wiwitadityasaputra.user.api;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserCtrl.class, secure = false)
public class UserCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private UserHelper userHelper;
	
	@Test
	public void editUser_hasLoggedUser_200() throws Exception {
		given(userHelper.getLoggedUser()).willReturn(new User());
		given(userRepo.findByInitial(anyString())).willReturn(null);
		given(userRepo.findOne(anyInt())).willReturn(new User());
		putEditUser(HttpStatus.OK.value());
		
		given(userRepo.findByInitial(anyString())).willReturn(new User());
		putEditUser(HttpStatus.OK.value());
	}
	
	@Test
	public void editUser_initial_400() throws Exception {
		User user1 = new User();
		user1.setId(1);
		User user2 = new User();
		user2.setId(2);
		given(userHelper.getLoggedUser()).willReturn(user1);
		given(userRepo.findByInitial(anyString())).willReturn(user2);
		given(userRepo.findOne(anyInt())).willReturn(new User());
		
		putEditUser(HttpStatus.BAD_REQUEST.value());
	}
	
	public void putEditUser(int httpStatus) throws Exception {
		UserReq input = new UserReq();
		input.setBase64ProfilePicture("YW55IGNhcm5hbCBwbGVhc3VyZS4=");
		input.setFileType("any-string");
		input.setInitial("any-string");
		
		assertEquals(httpStatus, mockMvc.
			perform(
				put(ApiPath.API_USER).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(input))
			).
			andReturn().
			getResponse().
			getStatus());		
	}
}
