package info.wiwitadityasaputra.util.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class ApiPathTest {

	@Test(expected = InvocationTargetException.class)
	public void sssinstanceOfApiPath() throws Exception {
		Constructor<ApiPath> c = ApiPath.class.getDeclaredConstructor();
		c.setAccessible(true);
		ApiPath u = c.newInstance();
	}
}
