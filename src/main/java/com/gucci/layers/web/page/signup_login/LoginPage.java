package com.gucci.layers.web.page.signup_login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.home.HomePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class LoginPage extends BasePage<LoginPage> {

    public SelenideElement newUserSignUpHeader = $x("//h2[text()='New User Signup!']");
    public SelenideElement signUpInputName = $x("//input[@name='name']");
    public SelenideElement signUpInputEmail = $x("//input[@data-qa='signup-email']");
    public SelenideElement signUpBtn = $x("//button[@data-qa='signup-button']");
    public SelenideElement emailAddressAlreadyExistMessage = $x("//p[normalize-space(text())='Email Address already exist!']");

    public SelenideElement loginHeader = $x("//h2[text()='Login to your account']");
    public SelenideElement loginEmail = $x("//input[@data-qa='login-email']");
    public SelenideElement loginPassword = $x("//input[@data-qa='login-password']");
    public SelenideElement loginBtn = $x("//button[@data-qa='login-button']");
    public SelenideElement incorrectParamsMessage = $x("//p[text()='Your email or password is incorrect!']");

    public SelenideElement homeBtn = $x("//a[normalize-space(text())='Home']");

    @Override
    public LoginPage waitForPageLoaded() {
        newUserSignUpHeader.shouldHave(Condition.exactText("New User Signup!"));
        return this;
    }

    @Step("input user name {0}")
    public LoginPage fillName(String name) {
        elementManager.input(signUpInputName, name);
        return this;
    }

    @Step("input user email {0}")
    public LoginPage fillEmail(String email) {
        elementManager.input(signUpInputEmail, email);
        return this;
    }

    @Step("click sign up button")
    public <T extends BasePage<T>> T clickSignUpBtn(Class<T> nextPageClas) {
        elementManager.click(signUpBtn);
        return Selenide.page(nextPageClas);
    }

    @Step("Verify 'Login to your account' is visible")
    public LoginPage loginToYourAccountVisible() {
        loginHeader.shouldHave(Condition.exactText("Login to your account"));
        return this;
    }

    @Step("Input login email, input login password and click login Button")
    public <T extends BasePage<T>> T loginUser(String email, String password, Class<T> nextPageClas) {
        elementManager.input(loginEmail, email);
        elementManager.input(loginPassword, password);
        elementManager.click(loginBtn);

        return Selenide.page(nextPageClas);
    }

    @Step("Click home button")
    public HomePage clickHome() {
        elementManager.click(homeBtn);
        return Selenide.page(HomePage.class);
    }

    @Step("Verify login with incorrect parameters")
    public LoginPage paramsIncorrectErrorMessage() {
        incorrectParamsMessage.shouldHave(Condition.exactText("Your email or password is incorrect!"));
        return this;
    }

    @Step("Verify email already exist message")
    public LoginPage verifyEmailAddressAlreadyExistMessage() {
        emailAddressAlreadyExistMessage.shouldHave(Condition.exactText("Email Address already exist!"));
        return this;
    }
}
