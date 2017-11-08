package info.wiwitadityasaputra.util.api;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiPath.API_GITSTATUS)
public class GitStatusCtrl extends AbstractCtrl {

	@RequestMapping(method = RequestMethod.GET)
	public String getGitStatus() throws Exception {

		JSONObject result = new JSONObject();
		result.put("gitId", gitId);
		result.put("gitIdAbbrev", gitIdAbbrev);
		result.put("gitCommitName", gitCommitName);
		result.put("gitCommitMsg", gitCommitMsg);
		return result.toString();
	}
}
