package com.gucci.layers.web.page.signup_login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import static com.codeborne.selenide.Selenide.$;

public class DeleteAccountPage extends BasePage<DeleteAccountPage> {

    public SelenideElement accountDeletedHeader = $("h2[data-qa='account-deleted'] b");
    public SelenideElement continueBtn = $("a[data-qa='continue-button']");

    @Override
    public DeleteAccountPage waitForPageLoaded() {
        accountDeletedHeader.shouldHave(Condition.exactText("Account Deleted!"));
        return this;
    }

    public HomePage clickContinue() {
        elementManager.click(continueBtn);
        return Selenide.page(HomePage.class);
    }
}
