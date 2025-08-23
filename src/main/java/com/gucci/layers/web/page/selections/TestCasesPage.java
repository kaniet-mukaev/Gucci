package com.gucci.layers.web.page.selections;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.gucci.layers.web.page.BasePage;

import static com.codeborne.selenide.Selenide.$x;

public class TestCasesPage extends BasePage<TestCasesPage> {

    public SelenideElement testCasesOrange = $x("//a[@href='/test_cases' and contains(@style, 'orange')]");

    @Override
    public TestCasesPage waitForPageLoaded() {
        testCasesOrange.shouldHave(Condition.attribute("style", "color: orange;"));
        return this;
    }
}
