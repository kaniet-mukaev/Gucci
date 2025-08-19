import com.gucci.entities.User;
import com.gucci.layers.web.page.home.HomePage;
import org.junit.jupiter.api.Test;

public class RegistrationTest extends BaseTest{

    @Test
    public void registerNewUserTest() {

        User user = new User();

        open(HomePage.class)
                .waitForPageLoaded()
                .clickSignupLoginBtn()
                .waitForPageLoaded();
    }
}
