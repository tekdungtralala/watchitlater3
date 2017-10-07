package info.wiwitadityasaputra.api;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.util.AuthModel;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/me")
	public AuthModel me() throws Exception {
		logger.info("call GET /api/auth/me");
		try {
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			AuthModel am = (AuthModel) obj;
			am.setCreatedDate(null);
			return am;
		} catch (Exception e) {
			throw new ForbiddenException();
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/signin")
	public void signIn() throws Exception {
		logger.info("call POST /api/auth/signin");
		String email = "wiwit.aditya.saputra@gmail.com";
		AuthModel am = new AuthModel(UUID.randomUUID().toString(), email);

		List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
		grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(am, "password", grantedAuth);
		logger.info("create auth");

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/signout")
	public void signOut(HttpServletRequest request) throws Exception {
		logger.info("call POST /api/auth/signout");
		request.logout();
	}
}
