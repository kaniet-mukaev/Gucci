import com.gucci.entities.User;
import com.gucci.entities.UserGenerated;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseWebTest{

    @Description("Test Case 2: Login User with correct email and password")
    @Test
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

    @Description("Test Case 3: Login User with incorrect email and password")
    @Test
    public void loginTestIncorrectParams() {

        String email = "aliia1@gmail.com";
        String password = "123";

        var loginPage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBetweenSection("Signup / Login", LoginPage.class)
                .loginToYourAccountVisible()
                .loginUser(email, password, LoginPage.class)
                .paramsIncorrectErrorMessage();

    }

    @Description("Test Case 4: Logout User")
    @Test
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
