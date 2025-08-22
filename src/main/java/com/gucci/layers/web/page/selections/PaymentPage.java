package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;
import net.datafaker.Faker;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class PaymentPage extends BasePage<PaymentPage> {
    public SelenideElement nameOnCard = $x("//input[@name='name_on_card']");
    public SelenideElement card_number = $x("//input[@name='card_number']");
    public SelenideElement cvc = $x("//input[@name='expiry_month']");
    public SelenideElement expiry_month = $x("//input[@name='cvc']");
    public SelenideElement expiry_year = $x("//input[@data-qa='expiry-year']");
    public SelenideElement payAndConfirmOrder = $x("//button [text() = 'Pay and Confirm Order']");
    public SelenideElement placeSuccessMessage = $x("(//div[@class = 'alert-success alert'])[1]");


Faker faker = new Faker();


    @Override
    public PaymentPage waitForPageLoaded() {
        return this;
    }
    @Step("Fill in the payment fields with fake data")
    public PaymentPage fillPaymentOrder() {
        elementManager.input(nameOnCard, faker.name().fullName());
        elementManager.input(card_number, faker.finance().creditCard().replaceAll("-", ""));
        elementManager.input(cvc, String.valueOf(faker.number().numberBetween(100, 999)));
        elementManager.input(expiry_month, String.valueOf(faker.number().numberBetween(1, 12)));
        elementManager.input(expiry_year, String.valueOf(faker.number().numberBetween(25, 30))); // 2025-2030
        return this;
    }

  @Step("click pay and confirm order")
    public PaymentPage clickPayAndConfirmOrder () {
        elementManager.click(payAndConfirmOrder);
        return this;
    }

    @Step("Your order has been placed successfully!")
    public HomePage verifyOrderPlacedSuccessfully(){
        placeSuccessMessage.shouldHave(Condition.exactText("Your order has been placed successfully!"));
        return page(HomePage.class);
    }
}