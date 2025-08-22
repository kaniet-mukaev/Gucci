
import com.gucci.data.Sections;
import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.selections.CartPage;
import com.gucci.layers.web.page.selections.ProductsPage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static com.gucci.data.Sections.*;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
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
                .verifyOrderPlacedSuccessfully()
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
                .verifyOrderPlacedSuccessfully()
                .clickDeleteAccount()
                .waitForPageLoaded();
    }

    @Test
    @DisplayName("Place Order: Login before Checkout")
    @Owner("Kaniet")
    @Tag("Test case: 16")
    public void placeOrderBeforeCheckoutTest() {

        User firstUser = UserGenerated.randomUser();

        var blue_top = "Blue Top";
        var men_tShirt = "Men Tshirt";

        open("", HomePage.class)
                .waitForPageLoaded()
                .clickSignupLoginBtn()
                .waitForPageLoaded()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(firstUser)
                .waitForPageLoaded()
                .clickContinueBtn()
                .verifyLoggedAsUserNameVisible(firstUser.getName())
                .addProductToCart(blue_top)
                .clickContinueShopping()
                .addProductToCart(men_tShirt)
                .clickContinueShopping()
                .switchBetweenSection(CART_PAGE, CartPage.class)
                .waitForPageLoaded()
                .clickProceedCheckout()
                .verifyAddressDetailsText()
                .verifyReviewYourOrderText()
                .inputFormControl()
                .clickPlaceOrder()
                .fillPaymentOrder()
                .clickPayAndConfirmOrder()
                .verifyOrderPlacedSuccessfully()
                .clickDeleteAccount()
                .waitForPageLoaded()
                .clickContinue();
    }

    @Test
    @DisplayName("Remove Products From Cart")
    @Owner("Kaniet")
    @Tag("Test case: 17")
    public void removeProductsFromCartTest() {

        var blue_top = "Blue Top";
        var men_tShirt = "Men Tshirt";

        open("", HomePage.class)
                .waitForPageLoaded()
                .addProductToCart(blue_top)
                .clickContinueShopping()
                .addProductToCart(men_tShirt)
                .clickContinueShopping()
                .switchBetweenSection(CART_PAGE, CartPage.class)
                .waitForPageLoaded()
                .clickDeleteProductFromCart()
                .clickDeleteProductFromCart()
                .verifyThatProductIsRemovedFromTheCart();
    }

    @Test
    @DisplayName("View Category Products")
    @Owner("Kaniet")
    @Tag("Test case: 18")
    public void viewCategoryProductsTest() {

        open("", HomePage.class)
                .verifyLeftSideBarIsVisible()
                .clickWomenCategory()
                .clickWomenSubCategory()
                .waitForPageLoaded()
                .clickMenCategoryAndSubCategory()
                .verifyMenTShirtsProductsHeaderIsVisible();
    }

    @Test
    @DisplayName("View & Cart Brand Products")
    @Owner("Kaniet")
    @Tag("Test case: 19")
    public void viewAndCartBrandsProductsTest() {

        open("products", ProductsPage.class)
                .verifyBrandsOnLeftSideBarAreVisible()
                .waitForPageLoaded()
                .clickBrandByName("Polo")
                .waitForPageLoaded()
                .clickBrandByName("H&M")
                .waitForPageLoaded();
    }

    @Test
    @DisplayName("Search Products and Verify Cart After Login")
    @Owner("Kaniet")
    @Tag("Test case: 20")
    public void searchProductsAndVerifyCartAfterLoginTest() {

        User firstUser = UserGenerated.randomUser();
        SoftAssertions softly = new SoftAssertions();

        var email = firstUser.getEmail();
        var password = firstUser.getPassword();

        var blue_top = "Blue Top";
        var men_tShirt = "Men Tshirt";

        open("", HomePage.class)
                .waitForPageLoaded()
                .clickSignupLoginBtn()
                .waitForPageLoaded()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(firstUser)
                .waitForPageLoaded()
                .clickContinueBtn()
                .verifyLoggedAsUserNameVisible(firstUser.getName())
                .clickLogout();

        var cartPage =  open("", HomePage.class)
                .switchBetweenSection(PRODUCT_PAGE, ProductsPage.class)
                .waitForPageLoaded()
                .fillSearchInput(blue_top)
                .clickSearchBtn()
                .verifySearchedProductsHeaderIsVisible()
                .verifyAllSearchedProductsAreVisible(blue_top)
                .addProductToCart(blue_top)
                .clickViewCart();

        step("Проверка выбранных продуктов в корзине", () -> {
            List<String> expectedProducts = List.of(blue_top);
            softly.assertThat(cartPage.verifyProductsInCart())
                    .as("Verify products in cart")
                    .isEqualTo(expectedProducts);
        });

        open("", HomePage.class)
                .waitForPageLoaded()
                .clickSignupLoginBtn()
                .waitForPageLoaded()
                .loginUser(email, password, HomePage.class)
                .switchBetweenSection(CART_PAGE, CartPage.class);

        step("Проверка выбранных продуктов в корзине", () -> {
            List<String> expectedProducts = List.of(blue_top);
            softly.assertThat(cartPage.verifyProductsInCart())
                    .as("Verify products in cart")
                    .isEqualTo(expectedProducts);
        });

        softly.assertAll();
    }
}