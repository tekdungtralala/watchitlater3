package info.wiwitadityasaputra.user.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

	private Logger logger = LogManager.getLogger(UserCtrl.class);

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserHelper userHelper;

	@RequestMapping(method = RequestMethod.PUT)
	public void editUser(@RequestBody UserInput userInput) {
		logger.info("PUT " + ApiPath.API_USER);
		logger.info(" initial = " + userInput.getInitial());

		User loggedUser = userHelper.getLoggedUser();
		User findedUser = userRepo.findByInitial(userInput.getInitial());

		boolean validInitial = findedUser == null || findedUser.getId() == loggedUser.getId();
		if (!validInitial) {
			throw new BadRequestException("Initial already taken.");
		}

		loggedUser.setInitial(userInput.getInitial());
		userRepo.save(loggedUser);
		logger.info("  update user");
	}
}
