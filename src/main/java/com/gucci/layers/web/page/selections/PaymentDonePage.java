package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import com.gucci.layers.web.page.signup_login.DeleteAccountPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PaymentDonePage extends BasePage<PaymentDonePage> {

    public SelenideElement header = $(By.id("header"));
    public SelenideElement orderPlacedHeader = $x("//b[text()='Order Placed!']");
    public SelenideElement downloadInvoice = $x("//a[text()='Download Invoice']");
    public SelenideElement continueBtn = $x("//a[text()='Continue']");
    public SelenideElement orderSuccessMessage = $x("//p[text()='Congratulations! Your order has been confirmed!']");
    public SelenideElement deleteAccountBtn = header.$("a[href='/delete_account']");

    @Override
    public PaymentDonePage waitForPageLoaded() {
        orderPlacedHeader.shouldHave(Condition.exactText("Order Placed!"));
        return this;
    }

    @Step("verify invoice is downloaded successfully")
    public PaymentDonePage clickDownloadInvoiceBtn() {
        elementManager.click(downloadInvoice);
        return this;
    }

    @Step("verify invoice is downloaded successfully")
    public PaymentDonePage verifyInvoiceIsDownloaded() {
        File downloadedInvoice = $("a[href*='download_invoice']").download();
        assertThat(downloadedInvoice.exists()).isTrue();
        assertThat(downloadedInvoice.length()).isGreaterThan(0);
        return this;
    }

    @Step("Click continue button")
    public HomePage clickContinueBtn() {
        elementManager.click(continueBtn);
        return page(HomePage.class);
    }

    @Step("Your order has been placed successfully!")
    public PaymentDonePage verifyOrderPlacedSuccessfully() {
        orderSuccessMessage.shouldHave(Condition.exactText("Congratulations! Your order has been confirmed!"));
        return this;
    }

    @Step("Click delete account")
    public DeleteAccountPage clickDeleteAccount() {
        elementManager.click(deleteAccountBtn);
        return page(DeleteAccountPage.class);
    }
}
