package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class ProductsPage extends BasePage<ProductsPage> {

    public SelenideElement allProductsHeader = $x("//h2[text()='All Products']");
    public SelenideElement condition = $x("//b[text()='Condition:']");
    public SelenideElement brandsOnLeftSideBar = $x("//div[@class='brands-name']");
    public SelenideElement featuresItemsForm = $(".features_items");
    public ElementsCollection products = featuresItemsForm.$$x("div[class='col-sm-4']");
    public SelenideElement searchInput = $("input[name='search']");
    public SelenideElement searchBtn = $("input[name='search'] + button");
    public SelenideElement searchedProductsHeader = $x("//h2[text()='Searched Products']");
    public ElementsCollection searchProductList = featuresItemsForm.$$("col-sm-4");

    @Override
    public ProductsPage waitForPageLoaded() {
        allProductsHeader.shouldHave(Condition.exactText("All Products"));
        return this;
    }

    @Step("click view product")
    public ProductDetailsPage clickViewProduct(String productName) {
        SelenideElement element = $x("//p[normalize-space(text())='" + productName + "']/ancestor::div[contains(@class,'product')]//a[text()='View Product']");
        element.scrollTo().shouldBe(Condition.visible).click();
        return page(ProductDetailsPage.class);
    }

    @Step("fill search input")
    public ProductsPage fillSearchInput(String text) {
        elementManager.input(searchInput, text);
        return this;
    }

    @Step("click search btn")
    public ProductsPage clickSearchBtn() {
        elementManager.click(searchBtn);
        return this;
    }

    @Step("Verify that Brands are visible on left side bar")
    public ProductsPage verifyBrandsOnLeftSideBarAreVisible() {
        brandsOnLeftSideBar.shouldBe(Condition.visible);
        return this;
    }

    @Step("Click on any brand name")
    public BrandProductsPage clickBrandByName(String brandName) {
        SelenideElement brand = $x("//a[text()='" + brandName + "']");
        elementManager.click(brand);
        return page(BrandProductsPage.class);
    }

    @Step("Verify 'SEARCHED PRODUCTS' is visible")
    public ProductsPage verifySearchedProductsHeaderIsVisible() {
        searchedProductsHeader.shouldHave(Condition.exactText("Searched Products"));
        return this;
    }

    @Step("Verify all the products related to search are visible")
    public HomePage verifyAllSearchedProductsAreVisible(String productName) {
        for (SelenideElement element : searchProductList) {
            element.shouldHave(Condition.exactText(productName));
        }
        return page(HomePage.class);
    }
}
