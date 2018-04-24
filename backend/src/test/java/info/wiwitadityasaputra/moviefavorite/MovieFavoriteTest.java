package info.wiwitadityasaputra.moviefavorite;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.user.entity.User;
import testutility.AbstractEntityTest;

public class MovieFavoriteTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		MovieFavorite mf = new MovieFavorite();
		mf.setId(integer);
		mf.setFavorite(bol);
		mf.setPosition(integer);
		Movie m = new Movie();
		m.setId(integer);
		mf.setMovie(m);
		User u = new User();
		mf.setUser(u);
		
		assertInt(mf.getId());
		assertBoolean(mf.isFavorite());
		assertInt(mf.getPosition());
		assertEquals(m, mf.getMovie());
		assertInt(mf.getMovieId());
		assertEquals(u, mf.getUser());
		
	}
}
