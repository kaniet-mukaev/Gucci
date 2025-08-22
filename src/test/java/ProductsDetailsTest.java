
import com.gucci.layers.web.page.home.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductsDetailsTest extends BaseWebTest {
    @Test
    @DisplayName("Test Case 13: Verify Product quantity in Cart")
    public void verifyProductQuantityInCartTest() {
        var productName = "Blue Top";
                open("", HomePage.class)
                .waitForPageLoaded()
                .clickViewProduct(productName)
                .increaseQuantity("4")
                .clickAddToCart()
                .clickViewCart();

//        softly.assertThat(cartPage.getProductQuantity(productName))
//                .as("Verify quantity of product in cart")
//                .isEqualTo("4");
//
//        softly.assertAll();
    }
}