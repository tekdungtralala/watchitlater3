package info.wiwitadityasaputra.user.api;

import org.junit.Test;

import info.wiwitadityasaputra.util.api.ApiPath;

public class ApiPathTest {

	@Test(expected = IllegalStateException.class)
	public void createApiPathInstance_throwException() {
		new ApiPath();
	}
}
