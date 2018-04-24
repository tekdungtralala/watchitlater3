package info.wiwitadityasaputra.moviereview;

import org.junit.Test;

import testutility.AbstractEntityTest;

public class MovieReviewTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		MovieReview mr = new MovieReview();
		mr.setLatest(bol);
		assertBoolean(mr.isLatest());
	}
}
