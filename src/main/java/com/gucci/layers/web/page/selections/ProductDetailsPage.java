package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$x;

public class ProductDetailsPage extends BasePage<ProductDetailsPage> {
    public SelenideElement writeYourReview = $x("//a[@href='#reviews']");
    public SelenideElement increaseQuantity = $x("//input[@id = 'quantity']");
    public SelenideElement addToCart = $x("//button[@class='btn btn-default cart']");
    public SelenideElement viewCartBtn = $x("//a[@href='/view_cart']/u[text()='View Cart']");


    @Override
    public ProductDetailsPage waitForPageLoaded() {
        writeYourReview.shouldHave(Condition.exactText("Write Your Review"));
        return this;
    }


    @Step("Increase quantity to {0}")
    public ProductDetailsPage increaseQuantity(String quantity) {
        increaseQuantity.sendKeys(Keys.COMMAND + "a");
        increaseQuantity.sendKeys(Keys.DELETE);
        elementManager.input(increaseQuantity, quantity);
        return this;
    }

    @Step("verify product btn is open")
    public ProductDetailsPage clickAddToCart() {
        elementManager.click(addToCart);
        return this;
    }

    @Step("click view cart")
    public CartPage clickViewCart() {
        viewCartBtn.shouldBe(Condition.visible, Duration.ofSeconds(10));
        elementManager.click(viewCartBtn);
        return new CartPage();
    }
}