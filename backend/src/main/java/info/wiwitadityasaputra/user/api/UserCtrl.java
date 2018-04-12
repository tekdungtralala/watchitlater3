package info.wiwitadityasaputra.user.api;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.exception.BadRequestException;

@RestController
@RequestMapping(value = ApiPath.API_USER)
public class UserCtrl extends AbstractCtrl {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserHelper userHelper;

	@RequestMapping(method = RequestMethod.PUT)
	public void editUser(@RequestBody UserReq userInput) {
		userHelper.mustHasLoggedUser();

		if (!StringUtils.isEmpty(userInput.getInitial())) {
			User loggedUser = userHelper.getLoggedUser();
			User findedUser = userRepo.findByInitial(userInput.getInitial());

			boolean validInitial = findedUser == null || findedUser.getId() == loggedUser.getId();
			if (!validInitial) {
				throw new BadRequestException("Initial already taken.");
			}

			loggedUser.setInitial(userInput.getInitial());
			userRepo.save(loggedUser);
		}

		if (!StringUtils.isEmpty(userInput.getFileType())) {
			User loggedUser = userHelper.getLoggedUser();
			User findedUser = userRepo.findOne(loggedUser.getId());

			byte[] profilePicture = Base64.getDecoder().decode(userInput.getBase64ProfilePicture());

			findedUser.setProfilePicture(profilePicture);
			findedUser.setFileType(userInput.getFileType());
			userRepo.save(findedUser);
		}
	}
}
