package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.enums.ProductConditions;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductsPage extends BasePage<ProductsPage> {

    public SelenideElement featuresItemsForm = $(".features_items");
    public SelenideElement allProductsHeader = featuresItemsForm.$("h2[class='title text-center']");
    public ElementsCollection products = featuresItemsForm.$$x("div[class='col-sm-4']");
    public SelenideElement viewProductBtn1 = featuresItemsForm.$("div[class='col-sm-4'] a[href='/product_details/1']");

    @Override
    public ProductsPage waitForPageLoaded() {
        allProductsHeader.shouldHave(Condition.exactText("All Products"));
        return this;
    }

     public ProductDetail clickViewProduct() {
        elementManager.click(viewProductBtn1);
        return Selenide.page(ProductDetail.class);
     }

    public static class ProductDetail extends BasePage<ProductDetail> {

        public SelenideElement productInformationForm = $(".product-information");
        public SelenideElement productName = productInformationForm.$("h2");
        public SelenideElement productCategory = productInformationForm.$("h2 + p");
        public SelenideElement productPrice = productInformationForm.$("span span");
        public SelenideElement productAvailability = getProductCondition(ProductConditions.AVAILABILITY);
        public SelenideElement productCondition = getProductCondition(ProductConditions.CONDITIONS);
        public SelenideElement productBrand = getProductCondition(ProductConditions.BRAND);


        @Override
        public ProductDetail waitForPageLoaded() {
            return this;
        }

        public SelenideElement getProductCondition(ProductConditions conditions) {
            return $x("//p[normalize-space(text())='" + conditions.getText() + "']");
        }
    }

}
