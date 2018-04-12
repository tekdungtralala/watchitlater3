package info.wiwitadityasaputra;

import org.junit.Test;

public class ApplicationTest {

	@Test
	public void test_main() throws Exception {
		Application.main(new String[] { "--spring.profiles.active=test" });
	}
}
