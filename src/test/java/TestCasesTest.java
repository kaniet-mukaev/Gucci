import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.selections.TestCasesPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TestCasesTest extends BaseWebTest {

    @Test
    @DisplayName("Test Case 7: Verify Test Cases Page")
    @Tag("Test Case 7")
    public void testCasesTest() {

        var testCasesPage = open("", HomePage.class)
                .switchBetweenSection("Test Cases", TestCasesPage.class)
                .waitForPageLoaded();
    }
}
