package com.gucci.layers.web.page.signup_login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage<LoginPage> {
    public SelenideElement signUpHeaderIsVisible = $x("//h2[text()='New User Signup!']");
    public SelenideElement loginToYourAccountHeader = $x("//h2[text()='Login to your account']");
    public SelenideElement signUpName = $x("//input[@name='name']");
    public SelenideElement signUpEmail = $x("//input[@data-qa='signup-email']");
    public SelenideElement signUpButton = $x("//button[@data-qa='signup-button']");
    public SelenideElement loginEmailInput = $x("//input[@data-qa='login-email']");
    public SelenideElement loginPasswordInput = $x("//input[@data-qa='login-password']");
    public SelenideElement loginButton = $x("//button[@data-qa='login-button']");
    public SelenideElement incorrectEmailOrPasswordError = $x("//input[@placeholder='Password']/following-sibling::p[text()='Your email or password is incorrect!']");
    public SelenideElement homePageBtn = $x("//a[normalize-space(text())='Home']");
    public SelenideElement emailAlreadyExistError = $x("//p[normalize-space(text())='Email Address already exist!']");

    @Override
    public LoginPage waitForPageLoaded() {
        loginToYourAccountHeader.shouldHave(Condition.exactText("Login to your account"));
        return this;
    }

    @Step("input user name {0}")
    public LoginPage fillName(String name) {
        elementManager.input(signUpName, name);
        return this;
    }

    @Step("input user email {0}")
    public LoginPage fillEmail(String email) {
        elementManager.input(signUpEmail, email);
        return this;
    }

    @Step("click sign up button")
    public SignUpPage clickSignUpBtn() {
        elementManager.click(signUpButton);
        return Selenide.page(SignUpPage.class);
    }
}
