package info.wiwitadityasaputra.moviereview;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import info.wiwitadityasaputra.movie.Movie;
import info.wiwitadityasaputra.user.entity.User;

public class MovieReviewRespTest {

	@Test
	public void testSetterGetter() {
		User u = new User();
		u.setUserId("abc");
		u.setInitial(null);
		assertEquals("Anonymous", MovieReviewResp.toOutput(getDummyMovieReview(u)).getInitial());
		
		u.setInitial("initial");
		assertEquals(u.getInitial(), MovieReviewResp.toOutput(getDummyMovieReview(u)).getInitial());
	}

	private MovieReview getDummyMovieReview(User user) {
		MovieReview mr = new MovieReview();
		mr.setId(1);
		mr.setReview("abc");
		Movie m = new Movie();
		m.setId(1);
		m.setTitle("abc");
		mr.setMovie(m);

		mr.setUser(user);
		return mr;
	}
}
