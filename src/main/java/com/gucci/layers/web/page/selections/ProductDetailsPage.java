package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.enums.ProductCondition;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ProductDetailsPage extends BasePage<ProductDetailsPage> {

    public SelenideElement productInformationForm = $(".product-information");
    public SelenideElement productName = productInformationForm.$("h2");
    public SelenideElement productCategory = productInformationForm.$("h2 + p");
    public SelenideElement productPrice = productInformationForm.$("span span");
    public SelenideElement productAvailability = getProductCondition(ProductCondition.AVAILABILITY);
    public SelenideElement productCondition = getProductCondition(ProductCondition.CONDITIONS);
    public SelenideElement productBrand = getProductCondition(ProductCondition.BRAND);

    public SelenideElement writeYourReview = $x("//a[@href='#reviews']");
    public SelenideElement increaseQuantity = $x("//input[@id = 'quantity']");
    public SelenideElement addToCart = $x("//button[@class='btn btn-default cart']");
    public SelenideElement viewCartBtn = $x("//a[@href='/view_cart']/u[text()='View Cart']");
    public SelenideElement yourName = $x("//input[@id = 'name']");
    public SelenideElement emailAddress = $x("//input[@id = 'email']");
    public SelenideElement addReviewHere = $x("//textarea[@id='review']");
    public SelenideElement submit = $x("//button[@id = 'button-review']");
    public SelenideElement thankYouForYourReview = $x("//span[text() = 'Thank you for your review.']");

    @Override
    public ProductDetailsPage waitForPageLoaded() {
        writeYourReview.shouldHave(Condition.exactText("Write Your Review"));
        return this;
    }

    @Step("Get Product Condition")
    public SelenideElement getProductCondition(ProductCondition conditions) {
        return $x("//p[normalize-space(text())='" + conditions.getText() + "']");
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

    @Step("fill your name")
    public ProductDetailsPage fillYourName(String name) {
        elementManager.input(yourName, name);
        return this;
    }

    @Step("fill email address")
    public ProductDetailsPage fillEmailAddress(String email) {
        elementManager.input(emailAddress, email);
        return this;
    }

    @Step("fill fieal AddReviewHere")
    public ProductDetailsPage fillAddReviewHere(String textArea) {
        elementManager.input(addReviewHere, textArea);
        return this;
    }

    @Step("click view cart")
    public ProductDetailsPage clickSubmitBtn() {
        elementManager.click(submit);
        return this;
    }

    @Step("verify: Write Your Review")
    public ProductDetailsPage verifyThankYouForYourReview() {
        thankYouForYourReview.shouldHave(Condition.exactText("Thank you for your review."));
        return this;
    }
}