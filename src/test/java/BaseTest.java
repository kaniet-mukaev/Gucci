import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.gucci.layers.web.manager.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    private final String BASE_URL = "https://automationexercise.com/";
    public <T> T open(Class<T> clazz) {
        return Selenide.open(BASE_URL, clazz);
    }

    @BeforeAll
    public static void setUp() {
        WebDriverManager.configureBasicWebDriver();
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @AfterAll
    public static void tearDown() {
        SelenideLogger.removeListener("AllureSelenide");
    }
}
