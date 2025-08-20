import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.selections.ProductsPage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.utils.WaitManager;
import org.junit.jupiter.api.Test;

public class RegistrationTest extends BaseWebTest {

    @Test
    public void registerNewUserTest() {

        var homePage = open("", HomePage.class)
                .waitForPageLoaded();
        homePage.getBrands().forEach(System.out::println);
    }
}
