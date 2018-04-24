package info.wiwitadityasaputra.user.entity;

import org.junit.Test;

import testutility.AbstractEntityTest;

public class UserTest extends AbstractEntityTest {

	@Test
	public void testGettersSetters() {
		User user = new User();
		user.setProfilePicture(bytes);
		user.setFileType(str);
		user.setListMovieFavorite(set);
		user.setListMovieReview(set);
		
		assertArrayByte(user.getProfilePicture());
		assertString(user.getFileType());
		assertSet(user.getListMovieFavorite());
		assertSet(user.getListMovieReview());
	}
}
