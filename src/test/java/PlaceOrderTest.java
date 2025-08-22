
import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.gucci.data.Sections.SIGN_IN_LOGIN;

public class PlaceOrderTest extends BaseWebTest {

    @Test
    @DisplayName("Place Order: Register while Checkout")
    @Owner("Aidai")
    @Tag("Test case: 14")
    public void registerWhileCheckout() {

        User firstUser = UserGenerated.randomUser();

        var productName = "Blue Top";
        open("", HomePage.class)
                .waitForPageLoaded()
                .addProductToCart(productName)
                .clickViewCart()
                .waitForPageLoaded()
                .clickProceedCheckout()
                .clickLoginRegister()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(firstUser)
                .waitForPageLoaded()
                .clickContinueBtn()
                .verifyLoggedAsUserNameVisible(firstUser.getName())
                .clickCartBtn()
                .clickProceedCheckout()
                .verifyAddressDetailsText()
                .verifyReviewYourOrderText()
                .inputFormControl()
                .clickPlaceOrder()
                .fillPaymentOrder()
                .clickPayAndConfirmOrder()
                .verifyCongratulationsYourOrderHasBeenConfirmed()
                .clickDeleteAccount()
                .waitForPageLoaded();
    }

    @Test
    @DisplayName("Place Order: Login before Checkout")
    @Owner("Aidai")
    @Tag("Test case: 15")
    public void PlaceOrderLoginBeforeCheckout() {

        User firstUser = UserGenerated.randomUser();
        var productName = "Blue Top";
        open("", HomePage.class)
                .waitForPageLoaded()
                .switchBetweenSection(SIGN_IN_LOGIN, LoginPage.class)
                .waitForPageLoaded()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(SignUpPage.class)
                .signUpNewUser(firstUser)
                .clickContinueBtn()
                .verifyLoggedAsUserNameVisible(firstUser.getName())
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
                .clickDeleteAccount()
                .waitForPageLoaded();
    }
}