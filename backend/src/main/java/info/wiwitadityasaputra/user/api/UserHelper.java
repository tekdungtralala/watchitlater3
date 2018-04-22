package info.wiwitadityasaputra.user.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.util.api.exception.ForbiddenException;

@Component
public class UserHelper {

	private Logger logger = LogManager.getLogger(UserHelper.class);

	public User getLoggedUser() {
		try {
			SecurityContext context = SecurityContextHolder.getContext();
			if (context != null) {
				Authentication auth = context.getAuthentication();
				if (auth != null) {
					Object principal = auth.getPrincipal();
					if (principal instanceof User) {
						return (User) principal;
					}
				}
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public void mustHasLoggedUser() {
		User user = getLoggedUser();
		if (user == null)
			throw new ForbiddenException("Must login");
	}
}
