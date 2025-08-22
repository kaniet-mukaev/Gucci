package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.enums.ProductCondition;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;
import io.qameta.allure.Step;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductsPage extends BasePage<ProductsPage> {
    public SelenideElement allProductsHeader = $x("//h2[text()='All Products']");
    public SelenideElement allProducts = $x("//div[@class = 'features_items']/div[@class='col-sm-4']");
    public SelenideElement category = $x("//p[contains(normalize-space(.), 'Category:')]");
    public SelenideElement price = $x("//span[contains(text(), 'Rs.')]");
    public SelenideElement availability = $x("//b[text()='Availability:']");
    public SelenideElement condition = $x("//b[text()='Condition:']");
    public SelenideElement brand = $x("//b[text()='Brand:']");
    public SelenideElement searchProductInput = $(By.id("search_product"));
    public SelenideElement submitSearchBtn = $(By.id("submit_search"));
    public SelenideElement searchedProductsHeader = $x("//h2[text()='Searched Products']");


    public SelenideElement featuresItemsForm = $(".features_items");
    public ElementsCollection products = featuresItemsForm.$$x("div[class='col-sm-4']");
    public SelenideElement viewProductBtn1 = $x("//a[@href='/product_details/1']");
    public SelenideElement searchInput = $("input[name='search']");
    public SelenideElement searchBtn = $("input[name='search'] + button");

    public SelenideElement searchProductHeader = featuresItemsForm.$("h2[class='title text-center']");
    public ElementsCollection searchProductList = featuresItemsForm.$$("col-sm-4");

    @Override
    public ProductsPage waitForPageLoaded() {
        allProductsHeader.shouldHave(Condition.exactText("All Products"));
        return this;

    }

    @Step("click view product")
    public ProductsPage.ProductDetail clickViewProduct(String productName) {
        SelenideElement element = $x("//p[normalize-space(text())='" + productName + "']/ancestor::div[contains(@class,'product')]//a[text()='View Product']");
        element.scrollTo().shouldBe(Condition.visible).click();
        return Selenide.page(ProductDetail.class);
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

    public static class ProductDetail extends BasePage<ProductDetail> {

        public SelenideElement productInformationForm = $(".product-information");
        public SelenideElement productName = productInformationForm.$("h2");
        public SelenideElement productCategory = productInformationForm.$("h2 + p");
        public SelenideElement productPrice = productInformationForm.$("span span");
        public SelenideElement productAvailability = getProductCondition(ProductCondition.AVAILABILITY);
        public SelenideElement productCondition = getProductCondition(ProductCondition.CONDITIONS);
        public SelenideElement productBrand = getProductCondition(ProductCondition.BRAND);


        @Override
        public ProductDetail waitForPageLoaded() {
            return this;
        }

        public SelenideElement getProductCondition(ProductCondition conditions) {
            return $x("//p[normalize-space(text())='" + conditions.getText() + "']");
        }
    }

    }
