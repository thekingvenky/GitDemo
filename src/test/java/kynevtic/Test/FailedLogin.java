package kynevtic.Test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import kynevtic.TestComponents.BaseTest;

public class FailedLogin extends BaseTest{
	
	@Test(groups = {"Error Handling"}, retryAnalyzer = kynevtic.TestComponents.Retry.class)
	public void loginFailTest() throws InterruptedException, IOException {
		
//		launchApplication();
		landingPage.loginApplication("kratos@godofwar.com", "@Ragnarok202");
		String errorString = landingPage.failedLogin();
		Assert.assertEquals(errorString, "Incorrect email or password.");
//		Assert.assertFalse(true);
	}
}
