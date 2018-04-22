package info.wiwitadityasaputra.movie;

import org.junit.Test;

import info.wiwitadityasaputra.test.utility.AbstractEntityTest;

public class MovieTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		Movie m = new Movie();
		m.setTitle(str);
		m.setImdbId(str);
		m.setImdbRating(dbl);
		m.setReleased(date);
		m.setGenre(str);
		m.setYear(integer);
		m.setPlot(str);
		m.setJson(str);
		m.setListMovieSearch(set);
		m.setListMoviePoster(set);
		m.setListMovieFavorite(set);
		m.setListMovieReview(set);

		assertString(m.getTitle());
		assertString(m.getImdbId());
		assertDouble(m.getImdbRating());
		assertDate(m.getReleased());
		assertString(m.getGenre());
		assertInt(m.getYear());
		assertString(m.getPlot());
		assertString(m.getJson());
		assertSet(m.getListMoviePoster());
		assertSet(m.getListMovieSearch());
		assertSet(m.getListMovieFavorite());
		assertSet(m.getListMovieReview());
	}
}
