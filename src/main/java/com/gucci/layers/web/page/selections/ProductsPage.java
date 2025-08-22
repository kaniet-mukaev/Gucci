package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;


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


    @Override
    public ProductsPage waitForPageLoaded() {
        allProductsHeader.shouldBe(Condition.visible);
        return this;

    }


    }
