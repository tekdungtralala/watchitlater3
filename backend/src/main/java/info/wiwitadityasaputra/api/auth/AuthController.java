package info.wiwitadityasaputra.api.auth;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.api.ForbiddenException;
import info.wiwitadityasaputra.config.AppJWTManager;
import info.wiwitadityasaputra.config.AuthModel;

@CrossOrigin
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

	@RequestMapping(method = RequestMethod.GET, value = "/refresh-token")
	public SignatureModel refreshToken() {
		try {
			Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			AuthModel am = (AuthModel) obj;
			am.setCreatedDate(new Date());
			SignatureModel sm = new SignatureModel(AppJWTManager.fromAuthModel(am), am.getEmailAddress());
			return sm;
		} catch (Exception e) {
			throw new ForbiddenException();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/signin")
	public SignatureModel signIn() throws Exception {
		logger.info("call POST /api/auth/signin");
		String email = "wiwit.aditya.saputra@gmail.com";
		AuthModel am = new AuthModel(UUID.randomUUID().toString(), email);
		SignatureModel sm = new SignatureModel(AppJWTManager.fromAuthModel(am), email);
		return sm;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/signout")
	public void signOut(HttpServletRequest request) throws Exception {
		logger.info("call POST /api/auth/signout");
		request.logout();
	}
}
