package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;

public class CategoryProductsPage extends BasePage<CategoryProductsPage> {
    public SelenideElement womenDressProductsHeader = $x("//h2[text()='Women - Tops Products']");
    public SelenideElement menCategory = $x("//a[@href='#Men']");
    public SelenideElement menTShirtSubCategory = $x("//a[text() = 'Tshirts ']");
    public SelenideElement menTShirtsProductsHeader = $x("//h2[@class='title text-center']");

    @Override
    public CategoryProductsPage waitForPageLoaded() {
        womenDressProductsHeader.shouldHave(Condition.exactText("Women - Tops Products"));
        return this;
    }

    @Step("On left side bar, click on any sub-category link of 'Men' category")
    public CategoryProductsPage clickMenCategoryAndSubCategory() {
        elementManager.click(menCategory)
                .click(menTShirtSubCategory);
        return this;
    }

    @Step("Verify that user is navigated to that category page")
    public CategoryProductsPage verifyMenTShirtsProductsHeaderIsVisible() {
        menTShirtsProductsHeader.shouldHave(Condition.exactText("Men - Tshirts Products"));
        return this;
    }
}
