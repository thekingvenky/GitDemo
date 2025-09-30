package kynevtic.Test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import kynevtic.TestComponents.BaseTest;
import kynevtic.pageobjects.CartPagePOM;
import kynevtic.pageobjects.ProductsPagePOM;

public class VerifyProductPresence extends BaseTest{
	@Test
	public void verifyProductPresenceTest() throws InterruptedException, IOException {
		
//		launchApplication();
		landingPage.loginApplication("kratos@godofwar.com", "@Ragnarok2022");
		
		ProductsPagePOM productsPage = new ProductsPagePOM(driver);
		productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
		
		Assert.assertFalse(productsPage.verifyProductExists("ZARA COAT 1227"));
		Assert.assertTrue(productsPage.verifyProductExists("ZARA COAT 3"));
		
	}
	
	String productName;
	
	@Test
	public void verifyProductInCart() {
		landingPage.loginApplication("kratos@godofwar.com", "@Ragnarok2022");
		
		ProductsPagePOM productsPage = new ProductsPagePOM(driver);
		productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
		
		productName = "iphone 13 pro";
		
		productsPage.addProductToCart(productName);
		productsPage.waitForVisibilityOfElement(productsPage.toastElement);
		Assert.assertEquals(productsPage.toastMessageElement.getText(), "Product Added To Cart");
		productsPage.waitForInvisibilityOfElement(productsPage.toastElement);
	}
	
	@Test(dependsOnMethods = {"verifyProductInCart"})
	public void verifyActualProductInCart() {
		verifyProductInCart();
		CartPagePOM cartPage = new CartPagePOM(driver);
		cartPage.clickCart();
		assertTrue(cartPage.verifyProductExistsInCart(productName));
	}
}
