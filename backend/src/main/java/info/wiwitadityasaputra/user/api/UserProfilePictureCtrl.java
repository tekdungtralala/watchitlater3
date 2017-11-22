package info.wiwitadityasaputra.user.api;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import info.wiwitadityasaputra.user.entity.User;
import info.wiwitadityasaputra.user.entity.UserRepository;
import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;
import info.wiwitadityasaputra.util.api.exception.NotFoundException;

@RestController
@RequestMapping(value = ApiPath.API_USER_PROFILEPICTURE)
public class UserProfilePictureCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(UserProfilePictureCtrl.class);

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	public ResponseEntity<byte[]> getPPByUserId(@PathVariable("userId") String userId, HttpServletResponse response)
			throws Exception {
		User user = userRepo.findByUserId(userId);

		if (user == null)
			throw new NotFoundException("Can't find image with userId = " + userId);

		response.setContentType("image/jpeg");
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		headers.setContentType(MediaType.valueOf(user.getFileType()));
		return new ResponseEntity<>(user.getProfilePicture(), headers, HttpStatus.OK);
	}
}
