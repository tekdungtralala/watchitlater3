package info.wiwitadityasaputra.util.api;

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

import info.wiwitadityasaputra.util.config.AuthModel;

@RestController
@RequestMapping(value = AbstractCtrl.API_PATH_AUTH)
public class AuthController extends AbstractCtrl {

	private final static String ME = "/me";
	private final static String SIGNIN = "/signin";
	private final static String SIGNOUT = "/signout";

	private Logger logger = LogManager.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.GET, value = ME)
	public Object me() throws Exception {
		logger.info("GET " + AbstractCtrl.API_PATH_AUTH + ME);
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@RequestMapping(method = RequestMethod.GET, value = SIGNIN)
	public void signIn() throws Exception {
		logger.info("POST " + AbstractCtrl.API_PATH_AUTH + SIGNIN);
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

	@RequestMapping(method = RequestMethod.GET, value = SIGNOUT)
	public void signOut(HttpServletRequest request) throws Exception {
		logger.info("POST " + AbstractCtrl.API_PATH_AUTH + SIGNOUT);
		request.logout();
	}
}
