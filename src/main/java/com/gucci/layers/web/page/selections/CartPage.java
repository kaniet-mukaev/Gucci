package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class CartPage extends BasePage<CartPage> {
    public SelenideElement subscriptionHeader = $x("//div[@class='single-widget']/h2");
    public SelenideElement subscribeEmailInput = $(By.id("susbscribe_email"));
    public SelenideElement subscribeEmailBtn = $(By.id("subscribe"));
    public SelenideElement subscribedAlert = $x("//div[@class='alert-success alert']");
    public SelenideElement cartProducts = $x("//table[@id='cart_info_table']//tbody/tr");

    @Override
    public CartPage waitForPageLoaded() {
        subscriptionHeader.shouldBe(Condition.visible);
        return this;
    }
}
