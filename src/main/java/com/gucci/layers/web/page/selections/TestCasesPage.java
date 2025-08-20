package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TestCasesPage extends BasePage<TestCasesPage> {
    public SelenideElement testCasesOrange = $x("//a[@href='/test_cases' and contains(@style, 'orange')]");
    public SelenideElement testCasesHeader = $x("//h2[@class='title text-center']/child::b");

    @Override
    public TestCasesPage waitForPageLoaded() {
        testCasesOrange.shouldBe(Condition.visible);
        return this;
    }
}
