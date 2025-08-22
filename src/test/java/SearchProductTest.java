import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.selections.ProductsPage;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class SearchProductTest extends BaseWebTest {

    @Test
    @DisplayName("Test Case 9: Search Product")
    public void searchProductTest() {

        SoftAssertions softAssertions = new SoftAssertions();

        String productName = "Blue Top";

        var productPage = open("", HomePage.class)
                .waitForPageLoaded()
                .switchBetweenSection("Products", ProductsPage.class)
                .waitForPageLoaded()
                .fillSearchInput(productName)
                .clickSearchBtn();

        step("Verify that detail detail is visible: product name, category, price, availability, condition, brand", () -> {
            softAssertions.assertThat(productPage.searchProductHeader.getText())
                    .as("Verify search product header")
                    .isEqualTo("Searched Products");
        });

        step("Verify all the products related to search are visible", () -> {
            softAssertions.assertThat(productPage.searchProductList)
                    .as("Verify searchProductList")
                    .allMatch(SelenideElement::isDisplayed);
        });
    }
}