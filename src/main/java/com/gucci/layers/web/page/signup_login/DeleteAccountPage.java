package com.gucci.layers.web.page.signup_login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class DeleteAccountPage extends BasePage<DeleteAccountPage> {
    public SelenideElement accountDeletedMessage = $x("//b[text()='Account Deleted!']");
    public SelenideElement continueButton = $x("//a[@data-qa='continue-button']");

    @Override
    public DeleteAccountPage waitForPageLoaded() {
        accountDeletedMessage.shouldBe(Condition.visible);
        return this;
    }
}
