import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.selections.TestCasesPage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class VerifySubscriptionInHomeTest extends BaseWebTest {

    @Test
    @DisplayName("Test Case 10: Verify Subscription in home page")
    @Tag("Test Case 10")
    public void testCasesTest() {

        SoftAssertions softAssertions = new SoftAssertions();
        var homePage = open("", HomePage.class)
                .waitForPageLoaded()
                .scrollToSubscriptionHeader();

        step(" Verify text 'SUBSCRIPTION'", () -> {
            softAssertions.assertThat(homePage.single_widgetHeader.getText())
                    .as("SUBSCRIPTION text")
                    .isEqualTo("Subscription");
        });

        homePage.fillSubscriptionEmail("some@gmail.com")
                .clickSubscription();

        step("Verify success message 'You have been successfully subscribed!' is visible", () -> {
            softAssertions.assertThat(homePage.subscriptionHeader.getText())
                    .as("subscriptionHeader text")
                    .isEqualTo("You have been successfully subscribed!c");
        });

    }
}
