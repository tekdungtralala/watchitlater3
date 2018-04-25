package info.wiwitadityasaputra.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OmdbapiHelper {
	
	@Value("${com.omdbapi.apikey}")
	protected String apiKey;
	
	public JSONObject getMovie(String imdbId) throws JSONException {
		String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + imdbId;
		log.info("search movie url {}", url);
		
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		String response = restTemplate.getForObject(url, String.class);
		
		return new JSONObject(response);
	}
	
	public JSONObject getFullMovie(String imdbId) throws JSONException {
		String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&i=" + imdbId + "&plot=full";
		log.info("url: {}", url);
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		String response = restTemplate.getForObject(url, String.class);
		log.info(response);
		return new JSONObject(response);
	}
	
	public ResponseEntity<byte[]> getImage(String imdbId) {
		String url = "http://img.omdbapi.com/?apikey=" + apiKey + "&i=" + imdbId;
		log.info("url: " + url);
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		return restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
}
