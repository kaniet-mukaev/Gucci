package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class BrandProductsPage extends BasePage<BrandProductsPage> {

    public SelenideElement brandProductsHeader = $x("//h2[@class='title text-center']");

    @Override
    public BrandProductsPage waitForPageLoaded() {
        brandProductsHeader.shouldBe(Condition.visible);
        return this;
    }

    @Step("Click on any brand name")
    public BrandProductsPage clickBrandByName(String brandName) {
        SelenideElement brand = $x("//a[text()='" + brandName + "']");
        elementManager.click(brand);
        return page(BrandProductsPage.class);
    }
}
