package info.wiwitadityasaputra.user.api.randomUser;

import info.wiwitadityasaputra.util.api.AbstractCtrl;
import info.wiwitadityasaputra.util.api.ApiPath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = ApiPath.API_USER_RANDOM)
public class RandomUserCtrl extends AbstractCtrl {

	private Logger logger = LogManager.getLogger(RandomUserCtrl.class);

	@RequestMapping(method = RequestMethod.GET)
	public RandomUserOutput getRandomUser() throws JSONException {
		logger.info("GET " + ApiPath.API_USER_RANDOM);

		RestTemplate restTemplate = new RestTemplate();
		String url = "https://randomuser.me/api";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		JSONObject json = new JSONObject(response.getBody());
		JSONObject person0 = json.getJSONArray("results").getJSONObject(0);
		JSONObject person0Name = person0.getJSONObject("name");

		String fn = person0Name.getString("first").toLowerCase();
		String ln = person0Name.getString("last").toLowerCase();
		fn = fn.substring(0, 1).toUpperCase() + fn.substring(1).toLowerCase();
		ln = ln.substring(0, 1).toUpperCase() + ln.substring(1).toLowerCase();

		RandomUserOutput result = new RandomUserOutput();
		result.setEmail(person0.getString("email"));
		result.setFullName(fn + " " + ln);

		return result;
	}
}
