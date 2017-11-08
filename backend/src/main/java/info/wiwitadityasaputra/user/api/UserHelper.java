package info.wiwitadityasaputra.user.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import info.wiwitadityasaputra.user.entity.User;

@Component
public class UserHelper {

	public User getLoggedUser() {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user;
		} catch (Exception e) {

		}
		return null;
	}
}
