package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ContactUsPage extends BasePage<ContactUsPage> {
    public SelenideElement getInTouchHeader = $x("//h2[text()='Get In Touch']");
    public SelenideElement inputName = $x("//input[@name = 'name']");
    public SelenideElement inputEmail = $x("//input[@name = 'email']");
    public SelenideElement inputSubject = $x("//input[@name = 'subject']");
    public SelenideElement inputMessage = $x("//textarea[@id = 'message']");
    public SelenideElement submitBtn = $x("//input[@data-qa='submit-button']");
    public SelenideElement uploadFile = $x("//input[@name = 'upload_file']");
    public SelenideElement successHeader = $x("//div[@class='status alert alert-success' " +
            "and normalize-space(text())='Success! Your details have been submitted successfully.']");
    public SelenideElement homeBtn = $x("//span[normalize-space(text())='Home']");

    @Override
    public ContactUsPage waitForPageLoaded() {
        getInTouchHeader.shouldBe(Condition.visible);
        return this;
    }
}
