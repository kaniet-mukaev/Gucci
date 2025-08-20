import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.Test;

public class RegistrationTest extends BaseWebTest {

    @Description("Test Case 1: Register User")
    @Link("https://automationexercise.com/test_cases")
    @Owner("Aliia")
    @Test()
    public void registerNewUserTest() {

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
                .deleteAccountClick()
                .waitForPageLoaded()
                .clickContinue();

    }

    @Description("Test Case 5: Register User with existing email")
    @Link("https://automationexercise.com/test_cases")
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
