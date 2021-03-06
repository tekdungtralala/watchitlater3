package info.wiwitadityasaputra.user.api.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.user.api.UserHelper;
import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.exception.BadRequestException;
import info.wiwitadityasaputra.util.api.exception.ConflictException;
import info.wiwitadityasaputra.util.api.exception.ForbiddenException;

@RestController
public class AuthenticationCtrl extends AbstractCtrl {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserHelper userHelper;

	@RequestMapping(method = RequestMethod.GET, value = ApiPath.API_USER_AUTH_ME)
	public Object me() {
		userHelper.mustHasLoggedUser();
		return userHelper.getLoggedUser();
	}

	@RequestMapping(method = RequestMethod.POST, value = ApiPath.API_USER_AUTH_SIGNIN)
	public void signIn(@RequestBody SignUpInput input) {
		User user = userRepo.findByEmailAndPassword(input.getEmail(), input.getPassword());
		if (user == null) {
			throw new ForbiddenException("Cant find user by email & password above.");
		}

		if (!user.getPassword().equals(input.getPassword())) {
			throw new ForbiddenException("Cant find user by email & password above.");
		}

		List<GrantedAuthority> grantedAuth = new ArrayList<GrantedAuthority>();
		grantedAuth.add(new SimpleGrantedAuthority("ROLE_USER"));
		Authentication auth = new UsernamePasswordAuthenticationToken(user, "password", grantedAuth);

		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(auth);
		SecurityContextHolder.setContext(context);
	}

	@RequestMapping(method = RequestMethod.POST, value = ApiPath.API_USER_AUTH_SIGNOUT)
	public void signOut(HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = ApiPath.API_USER_AUTH_SIGNUP)
	public void signUp(@RequestBody NewUserInput input) {
		if (!input.getPassword().equals(input.getRePassword())) {
			throw new BadRequestException("password not same");
		}

		User user = userRepo.findByEmail(input.getEmail());
		if (user != null) {
			throw new ConflictException("Duplicate email address");
		} else {
			User newUser = new User();
			newUser.setUserId(UUID.randomUUID().toString());
			newUser.setEmail(input.getEmail());
			newUser.setPassword(input.getPassword());
			newUser.setFullName(input.getFullName());
			newUser.setInitial(null);
			userRepo.save(newUser);
		}
	}
}
