
import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import net.datafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class PlaceOrderTest extends BaseWebTest {
    @Test
    @DisplayName("Place Order: Register while Checkout")
    @Tag("Test case: 14")
    public void registerWhileCheckout() {
        String name = "Aidai";
        User firstUser = UserGenerated.randomUser();
        ;
        var productName = "Blue Top";
        Faker faker = new Faker();
        open("", HomePage.class)
                .waitForPageLoaded()
                .addProductToCart(productName)
                .clickViewCart()
                .waitForPageLoaded()
                .clickProceedCheckout()
                .clickLoginRegister()
                .fillName(name)
                .fillEmail(faker.internet().emailAddress())
                .clickSignUpBtn(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(firstUser)
                .waitForPageLoaded()
                .clickContinueBtn()
                .loggedAsUserNameVisible(name)
                .clickCartBtn()
                .clickProceedCheckout()
                .verifyAddressDetailsText()
                .verifyReviewYourOrderText()
                .inputFormControl()
                .clickPlaceOrder()
                .fillPaymentOrder()
                .clickPayAndConfirmOrder()
                .verifyCongratulationsYourOrderHasBeenConfirmed()
                .deleteAccountClick()
                .waitForPageLoaded();

    }

    @Test
    @DisplayName("Place Order: Login before Checkout")
    @Tag("Test case: 15")
    public void PlaceOrderLoginBeforeCheckout() {
        String name = "Aidai";
        User firstUser = UserGenerated.randomUser();
        var productName = "Blue Top";
        Faker faker = new Faker();
        open("", HomePage.class)
                .waitForPageLoaded()
                .clickSignupLoginBtn()
                .waitForPageLoaded()
                .fillName(name)
                .fillEmail(faker.internet().emailAddress())
                .clickSignUpBtn(SignUpPage.class)
                .signUpNewUser(firstUser)
                .clickContinueBtn()
                .loggedAsUserNameVisible(name)
                .addProductToCart(productName)
                .clickCartBtn()
                .waitForPageLoaded()
                .clickProceedCheckout()
                .verifyAddressDetailsText()
                .verifyReviewYourOrderText()
                .inputFormControl()
                .clickPlaceOrder()
                .fillPaymentOrder()
                .clickPayAndConfirmOrder()
                .verifyCongratulationsYourOrderHasBeenConfirmed()
                .deleteAccountClick()
                .waitForPageLoaded();

    }
}

