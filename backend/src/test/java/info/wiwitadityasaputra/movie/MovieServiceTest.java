package info.wiwitadityasaputra.movie;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

	@Mock
	private MovieRepository movieRepo;

	@InjectMocks
	private MovieService movieService;

	@Test
	public void findMovieByIds_validMovieIds_givenListMovie() throws JSONException {
		Movie m = new Movie();
		m.setId(123);
		given(movieRepo.findOne(anyInt())).willReturn(m);

		JSONArray movieIds = new JSONArray();
		movieIds.put(1);
		List<Movie> result = movieService.findMovieByIds(movieIds);
		for (Movie movie : result) {
			assertEquals(movie.getId(), m.getId());
		}
	}
}
