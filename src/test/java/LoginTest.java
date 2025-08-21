import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(Tags.SMOKE)
@Tag(Tags.WEB)
public class LoginTest extends BaseWebTest{

    @Test
    @DisplayName("Test Case 2: Login User with correct email and password")
    @Tag("Test Case 2")
    @Disabled("Disabled by Aliia")
    public void loginTestCorrectParams() {

        User firstUser = UserGenerated.randomUser();;

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
                 .clickHome()
                 .waitForPageLoaded()
                 .switchBetweenSection("Signup / Login", LoginPage.class)
                 .loginToYourAccountVisible()
                 .loginUser(firstUser.getEmail(), firstUser.getPassword(), HomePage.class)
                 .loggedAsUserNameVisible(firstUser.getName())
                 .deleteAccountClick()
                 .waitForPageLoaded()
                 .clickContinue();

    }

    @Test
    @DisplayName("Test Case 3: Login User with incorrect email and password")
    @Tag("Test Case 3")
    public void loginWithIncorrectParamsTest() {

        String email = "aliia1@gmail.com";
        String password = "123";

        var loginPage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBetweenSection("Signup / Login", LoginPage.class)
                .loginToYourAccountVisible()
                .loginUser(email, password, LoginPage.class)
                .paramsIncorrectErrorMessage();

    }


    @Test
    @DisplayName("Test Case 4: Logout User")
    @Tag("Test Case 4")
    public void logout() {

        User firstUser = UserGenerated.randomUser();;

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
                .waitForPageLoaded();

    }

}
