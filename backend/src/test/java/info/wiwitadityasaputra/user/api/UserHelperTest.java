package info.wiwitadityasaputra.user.api;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.exception.ForbiddenException;

public class UserHelperTest {

	@InjectMocks
	private UserHelper userHelper;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getLoggedUser_noUser_returnNull() {
		assertNull(userHelper.getLoggedUser());
	}

	@Test
	public void getLoggedUser_hasUser_returnNotNull() {
		User applicationUser = mock(User.class);
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(applicationUser);

		assertNotNull(userHelper.getLoggedUser());
	}

	@Test
	public void getLoggedUser_throwException_returnNull() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("");

		assertNull(userHelper.getLoggedUser());
	}

	@Test(expected = ForbiddenException.class)
	public void mustHasLoggedUser_noUser_throwException() {
		Authentication authentication = mock(Authentication.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("");

		userHelper.mustHasLoggedUser();
	}
}
