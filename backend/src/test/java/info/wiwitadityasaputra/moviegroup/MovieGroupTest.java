package info.wiwitadityasaputra.moviegroup;

import org.junit.Test;

import testutility.AbstractEntityTest;

public class MovieGroupTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		MovieGroup mg = new MovieGroup();
		mg.setId(integer);
		mg.setName(str);

		assertInt(mg.getId());
		assertString(mg.getName());
	}
}
