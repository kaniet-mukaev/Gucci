import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class RegistrationTest extends BaseWebTest {

    @Test()
    @DisplayName("Test Case 1: Register User")
    @Owner("Aliia")
    @Tag("Test Case 1")//идентификатор
    @Link("https://automationexercise.com/test_cases")
    public void registerNewUserTest() {

        User firstUser = UserGenerated.randomUser();
        SoftAssertions softly = new SoftAssertions();


        var homePage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBetweenSection("Signup / Login", LoginPage.class)
                .waitForPageLoaded()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(firstUser)
                .waitForPageLoaded()
                .clickContinueBtn()
                .loggedAsUserNameVisible(firstUser.getName())
                .deleteAccountClick()
                .waitForPageLoaded()
                .clickContinue();

//        softly.assertThat("Hello")
//                .as("Check greeting")
//                .isEqualTo("Hi");
//
//        softly.assertAll();

    }

    @Link("https://automationexercise.com/test_cases")
    @DisplayName("Test Case 5: Register User with existing email")
    @Owner("Aliia")
    @Test()
    public void registerUserWithExistingEmailTest() {

        User firstUser = UserGenerated.randomUser();

        var homePage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBetweenSection("Signup / Login", LoginPage.class)
                .waitForPageLoaded()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(SignUpPage.class)
                .waitForPageLoaded()
                .signUpNewUser(firstUser)
                .waitForPageLoaded()
                .clickContinueBtn()
                .loggedAsUserNameVisible(firstUser.getName())
                .clickLogout()
                .waitForPageLoaded()
                .fillName(firstUser.getName())
                .fillEmail(firstUser.getEmail())
                .clickSignUpBtn(LoginPage.class)
                .verifyEmailAddressAlreadyExistMessage();

    }

}
