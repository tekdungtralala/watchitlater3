package info.wiwitadityasaputra.test.utility;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class AbstractEntity {
	protected final static String str = "str";
	protected final static Double dbl = 1d;
	protected final static int integer = 1;
	protected final static Date date = new Date();
	protected final static Set set = Collections.EMPTY_SET;

	protected void assertString(String value) {
		assertEquals(this.str, value);
	}

	protected void assertDouble(Double value) {
		assertEquals(this.dbl, value);
	}

	protected void assertDate(Date value) {
		assertEquals(this.date, value);
	}

	protected void assertInt(int value) {
		assertEquals(this.integer, value);
	}

	protected void assertSet(Set value) {
		assertEquals(this.set, value);
	}
}
