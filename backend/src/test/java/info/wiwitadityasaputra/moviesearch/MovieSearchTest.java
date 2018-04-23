package info.wiwitadityasaputra.moviesearch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.test.utility.AbstractEntityTest;

public class MovieSearchTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		Movie m = new Movie();
		MovieSearch ms = new MovieSearch();
		ms.setMovie(m);
		ms.setQuery(str);
		ms.setImdbId(str);
		ms.setNotFound(bol);
		
		assertEquals(m, ms.getMovie());
		assertString(ms.getQuery());
		assertString(ms.getImdbId());
		assertBoolean(ms.isNotFound());
	}
}
