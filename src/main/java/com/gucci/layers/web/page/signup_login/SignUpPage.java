package com.gucci.layers.web.page.signup_login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.gucci.entities.User;
import com.gucci.layers.web.manager.ElementManager;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SignUpPage extends BasePage<SignUpPage> {

    public SelenideElement login_form = $(".login-form");
    public SelenideElement enterAccountInformationHeader = login_form.$("b");



    public SelenideElement password = $(By.id("password"));
    public SelenideElement selectDays = $(By.id("days"));
    public SelenideElement selectMonths = $(By.id("months"));
    public SelenideElement selectYears = $(By.id("years"));
    public SelenideElement firstNameInput = $(By.id("first_name"));
    public SelenideElement lastNameInput = $(By.id("last_name"));
    public SelenideElement companyInput = $(By.id("company"));
    public SelenideElement address1Input = $(By.id("address1"));
    public SelenideElement address2Input = $(By.id("address2"));
    public SelenideElement countrySelect = $(By.id("country"));
    public SelenideElement state = $(By.id("state"));
    public SelenideElement city = $(By.id("city"));
    public SelenideElement zipcode = $(By.id("zipcode"));
    public SelenideElement mobile_number = $(By.id("mobile_number"));
    public SelenideElement createAccountButton = $x("//button[@data-qa='create-account']");
    public SelenideElement name = $(By.id("name"));
    public SelenideElement email = $(By.id("email"));


    @Override
    public SignUpPage waitForPageLoaded() {
        enterAccountInformationHeader.shouldHave(Condition.exactText("Enter Account Information"));
        return this;
    }

    public AccountCreatedPage signUpNewUser(User user) {
        elementManager.click($x("//input[@value='"+ user.getTitle() +"']"))
                .verifyAttributeValue(name, "value", user.getName())
                .verifyAttributeValue(email, "value", user.getEmail())
                .input(password, user.getPassword());
        selectDateMonthYearCalendar(user.getDateOfBirth());

        elementManager.input(firstNameInput, user.getFirstName())
                .input(lastNameInput, user.getLastName())
                .input(companyInput, user.getCompany())
                .input(address1Input, user.getAddress1())
                .input(address2Input, user.getAddress2())
                .selectByText(countrySelect, user.getCountry().getCountry())
                .input(state, user.getState())
                .input(city, user.getCity())
                .input(zipcode, user.getZipcode())
                .input(mobile_number, user.getMobileNumber())
                .click(createAccountButton);
        return Selenide.page(AccountCreatedPage.class);
    }

    public SignUpPage selectDateMonthYearCalendar(String dateMonthYear) {
        String[] dateMonthYearParts = dateMonthYear.split("/");
        String day = dateMonthYearParts[0].substring(1);
        String month = dateMonthYearParts[1];
        String year = dateMonthYearParts[2];

        elementManager.selectByValue(selectDays, day);
        elementManager.selectByValue(selectMonths, month);
        elementManager.selectByValue(selectYears, year);
        return this;
    }

}
