package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.enums.ProductCondition;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductsPage extends BasePage<ProductsPage> {

    public SelenideElement featuresItemsForm = $(".features_items");
    public SelenideElement allProductsHeader = featuresItemsForm.$("h2[class='title text-center']");
    public ElementsCollection products = featuresItemsForm.$$x("div[class='col-sm-4']");
    public SelenideElement viewProductBtn1 = featuresItemsForm.$("div[class='col-sm-4'] a[href='/product_details/1']");
    public SelenideElement searchInput = $("input[name='search']");
    public SelenideElement searchBtn = $("input[name='search'] + button");

    public SelenideElement searchProductHeader = featuresItemsForm.$("h2[class='title text-center']");
    public ElementsCollection searchProductList = featuresItemsForm.$$("col-sm-4");

    @Override
    public ProductsPage waitForPageLoaded() {
        allProductsHeader.shouldHave(Condition.exactText("All Products"));
        return this;
    }

    @Step("сlick view product ")
     public ProductDetail clickViewProduct() {
        elementManager.click(viewProductBtn1);
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
