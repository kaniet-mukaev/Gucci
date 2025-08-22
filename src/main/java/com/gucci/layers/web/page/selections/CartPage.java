package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.utils.WaitManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$x;

public class CartPage extends BasePage<CartPage> {
    public SelenideElement subscriptionHeader = $x("//div[@class='single-widget']/h2");
    public SelenideElement subscribeEmailInput = $(By.id("susbscribe_email"));
    public SelenideElement subscribeEmailBtn = $(By.id("subscribe"));
    public SelenideElement subscribedAlert = $x("//div[@class='alert-success alert']");
    public SelenideElement subscribedSuccess = $x("//div[text() = 'You have been successfully subscribed!']");
    public SelenideElement cartProducts = $x("//table[@id='cart_info_table']//tbody/tr");
    public SelenideElement subscriptionText = $x("//h2[text()='Subscription']");

    public ElementsCollection productsInCartElements = $$x("//td[@class='cart_description']//h4/a");
    public ElementsCollection productPrices = $$x("//td[@class='cart_price']/p");
    public ElementsCollection productQuantities = $$x("//td[@class='cart_quantity']/button");
    public ElementsCollection productTotals = $$x("//td[@class='cart_total']/p");
    public SelenideElement proceedToCheckout = $x("//a[text() = 'Proceed To Checkout']");
    public SelenideElement loginRegister = $x("(//a[@href = '/login'])[2]");
    public SelenideElement addressDetailsText = $x("(//h2[text()='Address Details'])");
    public SelenideElement reviewYourOrderText = $x("(//h2[text()='Review Your Order'])");
    public SelenideElement formControl = $x("(//textarea[@class='form-control'])");
    public SelenideElement placeOrder = $x("(//a[text() = 'Place Order'])");
    public SelenideElement quantityInCart = $x(".cart_quantity_input");
    public ElementsCollection deleteProductFromCart = $$x("//a[@class = 'cart_quantity_delete']");
    public SelenideElement cartIsEmptyMessage = $x("//b[text() = 'Cart is empty!']");

    @Override
    public CartPage waitForPageLoaded() {
        subscriptionHeader.shouldBe(Condition.visible);
        return this;
    }

    @Step("Verify that selected products are in the cart")
    public List<String> verifyProductsInCart() {
        return productsInCartElements.texts();
    }

    @Step("input email {0}")
    public CartPage inputEmail(String email) {
        elementManager.input(subscribeEmailInput, email);
        elementManager.click(subscribeEmailBtn);
        return this;
    }

    @Step("Get product prices in cart")
    public List<String> getProductPrices() {
        return productPrices.texts();
    }

    @Step("Get product quantities in cart")
    public List<String> getProductQuantities() {
        return productQuantities.texts();
    }

    @Step("Get product totals in cart")
    public List<String> getProductTotals() {
        return productTotals.texts();
    }

    @Step("click proceed checkout")
    public CartPage clickProceedCheckout() {
        elementManager.click(proceedToCheckout);
        return this;
    }

    @Step("click login register")
    public LoginPage clickLoginRegister() {
        elementManager.click(loginRegister);
        return page(LoginPage.class);
    }

    @Step("verify address details text ")
    public CartPage verifyAddressDetailsText() {
        addressDetailsText.shouldBe(Condition.visible);
        return this;
    }

    @Step("verify review your order")
    public CartPage verifyReviewYourOrderText() {
        reviewYourOrderText.shouldBe(Condition.visible);
        return this;
    }

    @Step("fill form control")
    public CartPage inputFormControl() {
        elementManager.input(formControl, "Плохой товар");
        return this;
    }

    @Step("click by place order")
    public PaymentPage clickPlaceOrder() {
        elementManager.click(placeOrder);
        return page(PaymentPage.class);
    }

    @Step("get product quantity")
    public String getProductQuantity(String productName) {
        SelenideElement quantityInput = $x("//td[@class='cart_description']//a[text()='"
                + productName + "']/ancestor::tr//button");
        return quantityInput.getText();
    }

    @Step("click delete product from cart")
    public CartPage clickDeleteProductFromCart() {
        for (SelenideElement element : deleteProductFromCart) {
            elementManager.click(element);
        }
        return this;
    }

    @Step("verify that product is removed from the cart")
    public CartPage verifyThatProductIsRemovedFromTheCart() {
        cartIsEmptyMessage.shouldHave(Condition.exactText("Cart is empty!"));
        return this;
    }
}
