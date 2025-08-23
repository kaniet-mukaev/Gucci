package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import java.io.File;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;

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

    @Step("Enter name, email, subject and message")
    public ContactUsPage fillContactUsForm(String name, String email, String subject, String message) {
        String projectPath = System.getProperty("user.dir");
        String relativePath = "src/main/java/com/gucci/data/Снимок экрана 2025-08-13 в 13.37.42.png";
        File file = Paths.get(projectPath, relativePath).toFile();
        elementManager.input(inputName, name)
                .input(inputEmail, email)
                .input(inputSubject, subject)
                .input(inputMessage, message)
                .input(uploadFile, file.getAbsolutePath());
        return this;
    }

    @Step("Click Submit btn")
    public ContactUsPage clickSubmit() {
        elementManager.click(submitBtn);
        return this;
    }

    @Step("accept alert")
    public ContactUsPage clickAlertAccept() {
        alertHelper.acceptAlertIfPresented();
        return this;
    }

    @Step("click home btn")
    public HomePage clickHome() {
        elementManager.click(homeBtn);
        return Selenide.page(HomePage.class);
    }
}
