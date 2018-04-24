package info.wiwitadityasaputra.user.api.authentication;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.util.Status;

import info.wiwitadityasaputra.user.api.UserHelper;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.ApiPath;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthenticationCtrl.class, secure = false)
public class AuthenticationCtrlTest {

	@Autowired
	protected MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepo;
	@MockBean
	private UserHelper userHelper;
	@MockBean
    private MockHttpSession session;
	
	@Test
	public void me_200() throws Exception {
		given(userHelper.getLoggedUser()).willReturn(new User());
		
		mockMvc.
			perform(
				get(ApiPath.API_USER_AUTH_ME).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
			).
			andExpect(status().isOk());
	}
	
	@Test
	public void signIn_invalidUser_403() throws Exception {
		postSignIn(HttpStatus.FORBIDDEN.value());
	}
	
	@Test
	public void signIn_invalidPassword_403() throws Exception {
		User user = new User();
		user.setPassword("");
		given(userRepo.findByEmailAndPassword(anyString(), anyString())).willReturn(user);
		postSignIn(HttpStatus.FORBIDDEN.value());
	}
	
	@Test
	public void signIn_invalidPassword_200() throws Exception {
		User user = new User();
		user.setPassword(getDummySignUpInput().getPassword());
		given(userRepo.findByEmailAndPassword(anyString(), anyString())).willReturn(user);
		postSignIn(HttpStatus.OK.value());
	}
	
	private void postSignIn(int status) throws Exception {
		mockMvc.
			perform(
				post(ApiPath.API_USER_AUTH_SIGNIN).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(getDummySignUpInput()))
			).
			andExpect(status().is(status));
	}
	
	private SignUpInput getDummySignUpInput() {
		SignUpInput input = new SignUpInput();
		input.setEmail("email");
		input.setPassword("password");
		return input;
	}
	
	@Test
	public void signOut_200() throws Exception {
		Cookie c = new Cookie("test", "value");
		c.setMaxAge(123);

		mockMvc.
			perform(
				post(ApiPath.API_USER_AUTH_SIGNOUT).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				cookie(c).
				session(session)
			).
			andExpect(status().isOk());
	}
	
	@Test
	public void signup_passwordIsNotSame_400() throws Exception {
		NewUserInput input = new NewUserInput();
		input.setPassword("abc");
		input.setRePassword("def");
		mockMvc.
			perform(
				post(ApiPath.API_USER_AUTH_SIGNUP).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(input))
			).
			andExpect(status().isBadRequest());		
	}
	
	@Test
	public void signup_emailIsNotAvailable_409() throws Exception {
		given(userRepo.findByEmail(anyString())).willReturn(new User());
		
		NewUserInput input = new NewUserInput();
		input.setPassword("abc");
		input.setRePassword("abc");
		input.setEmail("email");
		mockMvc.
			perform(
				post(ApiPath.API_USER_AUTH_SIGNUP).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(input))
			).
			andExpect(status().isConflict());			
	}
	
	@Test
	public void signup_200() throws Exception {
		NewUserInput input = new NewUserInput();
		input.setPassword("abc");
		input.setRePassword("abc");
		input.setEmail("email");
		mockMvc.
			perform(
				post(ApiPath.API_USER_AUTH_SIGNUP).
				contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).
				content(new ObjectMapper().writeValueAsString(input))
			).
			andExpect(status().isOk());			
	}
}
