package info.wiwitadityasaputra.movie;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.wiwitadityasaputra.moviesearch.MovieSearch;
import info.wiwitadityasaputra.moviesearch.MovieSearchRepository;

@RunWith(MockitoJUnitRunner.class)
public class MovieHelperTest {

	@InjectMocks
	private MovieHelper movieHelper;

	@Mock
	private MovieSearchRepository movieSearchRepo;

	@Test
	public void fetchMovieByGroupName() throws IOException {
		String groupName = "2018-03-30_2018-04-05";
		List<MovieSearch> list = movieHelper.fetchMovieByGroupName(groupName);
		assertEquals(true, list.size() > 0);
	}
}
