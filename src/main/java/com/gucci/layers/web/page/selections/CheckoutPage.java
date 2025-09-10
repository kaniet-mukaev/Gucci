package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.entities.User;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.signup_login.DeleteAccountPage;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

public class CheckoutPage extends BasePage<CheckoutPage> {

    public SelenideElement addressDetailsHeader = $x("//h2[text()='Address Details']");
    public SelenideElement deleteAccountBtn = $x("//a[@href='/delete_account']");
    public SelenideElement reviewYourOrderText = $x("//h2[text()='Review Your Order']");
    public SelenideElement descriptionTextArea = $x("//textarea[@class='form-control']");
    public SelenideElement placeOrder = $x("//a[text() = 'Place Order']");

    @Override
    public CheckoutPage waitForPageLoaded() {
        addressDetailsHeader.shouldBe(Condition.visible);
        return this;
    }

    public CheckoutPage verifyDeliveryAndBillingAddressIsSameRegistration(User user) {
        SoftAssertions softly = new SoftAssertions();

        String expectedTitle = user.getTitle();
        if (!expectedTitle.endsWith(".")) {
            expectedTitle += ".";
        }

        softly.assertThat($x("//ul[@id='address_delivery']/li[@class='address_firstname address_lastname']")
                .getText()).isEqualTo(expectedTitle + " " + user.getFirstName() + " " + user.getLastName());
        softly.assertThat($x("(//ul[@id='address_delivery']/li[@class='address_address1 address_address2'])[1]")
                .getText()).isEqualTo(user.getCompany());
        softly.assertThat($x("(//ul[@id='address_delivery']/li[@class='address_address1 address_address2'])[2]")
                .getText()).isEqualTo(user.getAddress1());
        softly.assertThat($x("(//ul[@id='address_delivery']/li[@class='address_address1 address_address2'])[3]")
                .getText()).isEqualTo(user.getAddress2());
        softly.assertThat($x("//ul[@id='address_delivery']/li[@class='address_city address_state_name address_postcode']")
                .getText()).isEqualTo(user.getCity() + " " + user.getState() + " " + user.getZipcode());
        softly.assertThat($x("//ul[@id='address_delivery']/li[@class='address_country_name']")
                .getText()).isEqualTo(user.getCountry().getCountry());
        softly.assertThat($x("//ul[@id='address_delivery']/li[@class='address_phone']")
                .getText()).isEqualTo(user.getMobileNumber());

        softly.assertThat($x("//ul[@id='address_invoice']/li[@class='address_firstname address_lastname']")
                .getText()).isEqualTo(expectedTitle + " " + user.getFirstName() + " " + user.getLastName());
        softly.assertThat($x("(//ul[@id='address_invoice']/li[@class='address_address1 address_address2'])[1]")
                .getText()).isEqualTo(user.getCompany());
        softly.assertThat($x("(//ul[@id='address_invoice']/li[@class='address_address1 address_address2'])[2]")
                .getText()).isEqualTo(user.getAddress1());
        softly.assertThat($x("(//ul[@id='address_invoice']/li[@class='address_address1 address_address2'])[3]")
                .getText()).isEqualTo(user.getAddress2());
        softly.assertThat($x("//ul[@id='address_invoice']/li[@class='address_city address_state_name address_postcode']")
                .getText()).isEqualTo(user.getCity() + " " + user.getState() + " " + user.getZipcode());
        softly.assertThat($x("//ul[@id='address_invoice']/li[@class='address_country_name']")
                .getText()).isEqualTo(user.getCountry().getCountry());
        softly.assertThat($x("//ul[@id='address_invoice']/li[@class='address_phone']")
                .getText()).isEqualTo(user.getMobileNumber());

        softly.assertAll();
        return this;
    }

    @Step("Click delete account button")
    public DeleteAccountPage clickDeleteAccount() {
        elementManager.click(deleteAccountBtn);
        return page(DeleteAccountPage.class);
    }

    @Step("Verify Review Your Order is visible")
    public CheckoutPage verifyReviewOrderIsVisible() {
        reviewYourOrderText.shouldHave(Condition.exactText("Review Your Order"));
        return this;
    }

    @Step("Enter description in comment text area")
    public CheckoutPage inputDescriptionTextArea(String text) {
        elementManager.input(descriptionTextArea, text);
        return this;
    }

    @Step("Verify Review Your Order is visible")
    public PaymentPage clickPlaceOrderBtn() {
        elementManager.click(placeOrder);
        return page(PaymentPage.class);
    }

    @Step("verify address details text")
    public CheckoutPage verifyAddressDetailsText() {
        addressDetailsHeader.shouldHave(Condition.exactText("Address Details"));
        return this;
    }

    @Step("verify review your order")
    public CheckoutPage verifyReviewYourOrderText() {
        reviewYourOrderText.shouldBe(Condition.visible);
        return this;
    }
}
