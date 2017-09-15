package info.wiwitadityasaputra.api.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.config.AppJWTManager;
import info.wiwitadityasaputra.config.AuthModel;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	private Logger logger = LogManager.getLogger(AuthController.class);

	@RequestMapping(method = RequestMethod.POST, value = "/signin")
	public SignatureModel signIn() throws Exception {
		logger.info("call /api/auth/signin");
		String email = "wiwit.aditya.saputra@gmail.com";
		AuthModel am = new AuthModel("user", email);
		SignatureModel sm = new SignatureModel(AppJWTManager.fromAuthModel(am), email);
		return sm;
	}
}
