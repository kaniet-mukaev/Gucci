import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.gucci.layers.web.manager.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.gucci.config.ConfigurationManager.getAppConfig;

//сделать 6 по 10 тест кейсы
public class BaseWebTest {

    private final String BASE_URL = getAppConfig().baseUrl();
    public <T> T open(String endPoint, Class<T> clazz) {
        return Selenide.open(String.format("%s/%s", BASE_URL, endPoint), clazz);
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
