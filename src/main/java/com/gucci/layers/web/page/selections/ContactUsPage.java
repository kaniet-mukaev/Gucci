package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ContactUsPage extends BasePage<ContactUsPage> {

    public SelenideElement getInTouchHeader = $(".contact-form h2");
    public SelenideElement detailSubmittedMessage = $(".contact-form div[class='status alert alert-success']");

    public SelenideElement form_section = $("#form-section");
    public SelenideElement inputName = form_section.$("input[name='name']");
    public SelenideElement inputEmail = form_section.$("input[name='email']");
    public SelenideElement inputSubject = form_section.$("input[name='subject']");
    public SelenideElement inputMessage = form_section.$("textarea[name='message']");
    public SelenideElement uploadFile = form_section.$("input[type='file']");
    public SelenideElement submitBtn = form_section.$("input[type='submit']");
    public SelenideElement homeBtn = form_section.$("a[class='btn btn-success']");

    @Override
    public ContactUsPage waitForPageLoaded() {
        getInTouchHeader.shouldHave(Condition.exactText("Get In Touch"));
        return this;
    }
}
