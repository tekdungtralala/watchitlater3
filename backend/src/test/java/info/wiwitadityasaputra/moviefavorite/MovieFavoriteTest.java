package info.wiwitadityasaputra.moviefavorite;

import org.junit.Test;

import info.wiwitadityasaputra.test.utility.AbstractEntityTest;

public class MovieFavoriteTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		MovieFavorite mf = new MovieFavorite();
		mf.setId(integer);
		mf.setFavorite(bol);
		mf.setPosition(integer);
		
		assertInt(mf.getId());
		assertBoolean(mf.isFavorite());
		assertInt(mf.getPosition());
	}
}
