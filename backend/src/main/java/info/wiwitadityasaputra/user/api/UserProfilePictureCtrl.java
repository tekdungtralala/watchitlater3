package info.wiwitadityasaputra.user.api;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

@RestController
@RequestMapping(value = ApiPath.API_USER_PROFILEPICTURE)
public class UserProfilePictureCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(UserProfilePictureCtrl.class);
	private static String DEFAULT_IMAGE = "default-avatar.png";

	@Autowired
	private UserRepository userRepo;

	@RequestMapping(method = RequestMethod.GET, value = "/{userId}")
	public ResponseEntity<byte[]> getPPByUserId(@PathVariable("userId") String userId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		User user = userRepo.findByUserId(userId);
		byte[] bytes = null;
		if (user == null) {
			headers.setContentType(MediaType.IMAGE_JPEG);
			bytes = getDefaultImage();
		} else {
			headers.setContentType(MediaType.valueOf(user.getFileType()));
			bytes = user.getProfilePicture();
		}
		return new ResponseEntity(bytes, headers, HttpStatus.OK);
	}

	private byte[] getDefaultImage() throws IOException {
		Resource resource = new ClassPathResource(DEFAULT_IMAGE);
		return IOUtils.toByteArray(resource.getInputStream());
	}
}
