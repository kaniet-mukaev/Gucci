package com.gucci.layers.web.page.home;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import com.gucci.layers.web.page.signup_login.SignUpPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage <HomePage> {
    public SelenideElement homeOrange = $x("//a[@href='/' and contains(@style, 'orange')]");
    public SelenideElement signupLoginBtn = $x("//a[@href='/login']");
    public SelenideElement logoutBtn = $x("//i[@class='fa fa-lock']");
    public SelenideElement loggedInAsUsernameIsVisible = $x("//a[text()=' Logged in as ']");
    public SelenideElement deleteAccountButton = $x("//a[@href ='/delete_account']");
    public SelenideElement contactUsBtn = $x("//a[normalize-space(text())='Contact us']");
    public SelenideElement testCasesBtn = $x("//a[text()=' Test Cases']");
    public SelenideElement productsBtn = $x("//i[@class='material-icons card_travel']");
    public SelenideElement subscriptionHeader = $x("//div[@class='single-widget']/h2");
    public SelenideElement subscribeEmailInput = $(By.id("susbscribe_email"));
    public SelenideElement subscribeEmailBtn = $(By.id("subscribe"));
    public SelenideElement subscribedAlert = $x("//div[@class='alert-success alert']");
    public SelenideElement cart = $x("//a[@href='/view_cart']/i");

    @Override
    public HomePage waitForPageLoaded() {
        homeOrange.shouldHave(Condition.attribute("style","color: orange;"));
        return this;
    }

    @Step("click sign up/login button")
    public LoginPage clickSignupLoginBtn() {
        elementManager.click(signupLoginBtn);
        return Selenide.page(LoginPage.class);
    }


}
