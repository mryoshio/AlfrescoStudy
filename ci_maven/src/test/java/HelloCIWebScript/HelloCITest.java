package HelloCIWebScript;

import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.tachibanakikaku.webscripts.HelloCI;

/**
 * Unit test sample for HelloCI.
 */
public class HelloCITest extends TestCase {

	private HelloCI helloCi;
	private static final String NOT_PROPER_INSTANCE_ERROR = "Not property instantiated of HelloCI class.";

	public void testFieldClassIsNotNull() {
		assertNotNull(helloCi);
	}

	public void testClassPackage() {
		if (!(helloCi instanceof HelloCI)) {
			throw new AssertionFailedError(NOT_PROPER_INSTANCE_ERROR);
		}
	}

	public static Test suite() {
		return new TestSuite(HelloCITest.class);
	}

	protected void setUp() {
		helloCi = new HelloCI();
	}

	protected void tearDown() {

	}
}
