package info.wiwitadityasaputra.util;

import static org.junit.Assert.assertNotNull;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class OmdbapiHelperTest {

	@InjectMocks
	private OmdbapiHelper omdbapiHelper;

	@Test
	public void all() throws JSONException {
		ReflectionTestUtils.setField(omdbapiHelper, "apiKey", System.getenv("WATCHITLATER3_OMDBAPI_APIKEY"));
		String imdbid = "tt0111161";
		assertNotNull(omdbapiHelper.getMovie(imdbid));
		assertNotNull(omdbapiHelper.getFullMovie(imdbid));
		assertNotNull(omdbapiHelper.getImage(imdbid));
	}
}
