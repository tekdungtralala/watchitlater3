package info.wiwitadityasaputra.movieposter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.wiwitadityasaputra.movie.Movie;
import testutility.AbstractEntityTest;

public class MoviePosterTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		MoviePoster mp = new MoviePoster();
		mp.setId(integer);
		mp.setImgByte(bytes);
		mp.setFileType(str);
		Movie m = new Movie();
		mp.setMovie(m);
		mp.setImdbId(str);
		
		assertInt(mp.getId());
		assertArrayByte(mp.getImgByte());
		assertString(mp.getFileType());
		assertEquals(m, mp.getMovie());
		assertString(mp.getImdbId());
	}
}
