package com.gucci.layers.web.page.signup_login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class AccountCreatedPage extends BasePage<AccountCreatedPage> {

    public SelenideElement accountCreatedHeader = $(".row b");
    public SelenideElement continueBtn = $x("//a[@data-qa='continue-button']");

    @Override
    public AccountCreatedPage waitForPageLoaded() {
        accountCreatedHeader.shouldHave(Condition.exactText("Account Created!"), Duration.ofSeconds(15));
        return this;
    }

    public HomePage clickContinueBtn() {
        elementManager.click(continueBtn);
        return Selenide.page(HomePage.class);
    }
}
