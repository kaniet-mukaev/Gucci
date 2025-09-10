import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.gucci.layers.web.manager.WebDriverManager;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.gucci.config.ConfigurationManager.getAppConfig;

public class BaseWebTest {

    private final String BASE_URL = getAppConfig().baseUrl();

    @Step("Open the {1}")
    public <T> T open(String endPoint, Class<T> clazz) {
        T page = Selenide.open(String.format("%s/%s", BASE_URL, endPoint), clazz);
        WebDriverManager.configureRemoteDriver();
        return page;
    }

    @BeforeAll
    public static void setUp() {
        WebDriverManager.configureBasicWebDriver();
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
    }

    @BeforeEach
    public void clearCart(){
        closeWebDriver();
    }

    @AfterAll
    public static void tearDown() {
        SelenideLogger.removeListener("AllureSelenide");
    }
}
