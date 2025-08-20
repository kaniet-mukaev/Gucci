package com.gucci.layers.web.manager;

import com.codeborne.selenide.Configuration;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.headless;
import static com.gucci.config.ConfigurationManager.getAppConfig;

public class WebDriverManager {

    public static void configureBasicWebDriver() {
        browser = "chrome";
        headless = getAppConfig().headless();

        Configuration.browserSize = "1920x1080";

    }
}
