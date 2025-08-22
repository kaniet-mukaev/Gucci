package com.gucci.layers.web.page.home;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import com.gucci.layers.web.page.selections.ContactUsPage;
import com.gucci.layers.web.page.signup_login.DeleteAccountPage;
import com.gucci.layers.web.page.signup_login.LoginPage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class HomePage extends BasePage<HomePage> {
    public SelenideElement header = $(By.id("header"));
    public SelenideElement left_sidebar = $(".left-sidebar");
    public ElementsCollection brands = left_sidebar.$$x(".//div[@class='brands-name']//li[not(span[@class='pull-right'])]");
    public SelenideElement loggedInAsUserHeader = header.$("b");
    public SelenideElement deleteAccountBtn = header.$("a[href='/delete_account']");
    public SelenideElement logoutBtn = header.$("a[href='/logout']");
    public SelenideElement contactUsBtn = header.$("a[href='/contact_us']");

    public SelenideElement footer = $("#footer .footer-widget");
    public SelenideElement subscription = footer.$("h2");

    public SelenideElement homeOrange = $x("//a[@href='/' and contains(@style, 'orange')]");

    public SelenideElement single_widget = $(".single-widget");
    public SelenideElement single_widgetHeader = single_widget.$("h2");
    public SelenideElement inputSubscriptionEmail = single_widget.$("input[id='susbscribe_email']");
    public SelenideElement subscriptionBtn = single_widget.$("button");
    public SelenideElement subscriptionHeader = $x("//div[@class='alert-success alert']");

    @Override
    public HomePage waitForPageLoaded() {
        homeOrange.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }

    @Step("fill subscription email")
    public HomePage fillSubscriptionEmail(String email) {
        elementManager.input(inputSubscriptionEmail, email);
        return this;
    }

    @Step("Switch between section")
    public <T> T switchBetweenSection(String section, Class<T> pageClass) {
        SelenideElement sectionElement = header.$(By.xpath(
                ".//ul[@class='nav navbar-nav']//a[normalize-space(text())='" + section + "']"));
        elementManager.click(sectionElement);
        try {
            // Возвращаем новый объект указанного Page Object
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать экземпляр " + pageClass.getSimpleName(), e);
        }
    }

    @Step("click contact us button")
    public ContactUsPage clickContactUs() {
        elementManager.click(contactUsBtn);
        return Selenide.page(ContactUsPage.class);
    }

    @Step("Get brands")
    public List<String> getBrands() {
        List<String> brandsList = new ArrayList<>();
        for (SelenideElement element : brands) {
            brandsList.add(elementManager.getText(element));
        }
        return brandsList;
    }

    @Step("Logged as user name label visible")
    public HomePage verifyLoggedAsUserNameVisible(String name) {
        loggedInAsUserHeader.shouldHave(Condition.exactText(name));
        return this;
    }

    @Step("Click delete account")
    public DeleteAccountPage clickDeleteAccount() {
        elementManager.click(deleteAccountBtn);
        return Selenide.page(DeleteAccountPage.class);
    }

    @Step("Click logout")
    public LoginPage clickLogout() {
        elementManager.click(logoutBtn);
        return Selenide.page(LoginPage.class);
    }

    @Step("Verify subscribe header")
    public HomePage verifySubscribeHeader() {
        subscription.shouldHave(Condition.exactText("Subscription"));
        return this;
    }

    @Step("scroll to subscription header")
    public HomePage scrollToSubscriptionHeader() {
        single_widgetHeader.scrollTo();
        return this;
    }

    @Step("Click subscription")
    public HomePage clickSubscription() {
        elementManager.click(subscriptionBtn);
        return this;
    }
}

