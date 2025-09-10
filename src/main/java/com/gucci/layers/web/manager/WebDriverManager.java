package com.gucci.layers.web.manager;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.time.Duration;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.headless;
import static com.gucci.config.ConfigurationManager.getAppConfig;

public class WebDriverManager {

    public static void configureBasicWebDriver() {
        browser = "chrome";
        headless = getAppConfig().headless();
        if (getAppConfig().remote()) {
            Configuration.remote = getAppConfig().dockerUrl();
        } else Configuration.downloadsFolder = "temp";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-extensions",
                "--remote-allow-origins=*",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--disable-web-security",
                "--allow-running-insecure-content",
                "--window-size=1920,1080");
        if (getAppConfig().headless()) {
            chromeOptions.addArguments("--headless=new");
        }

        Configuration.browserCapabilities = chromeOptions;
    }

    public static void configureRemoteDriver() {
        if (getAppConfig().remote() && WebDriverRunner.hasWebDriverStarted()) {
            var driver = (RemoteWebDriver) WebDriverRunner.getWebDriver();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(60));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.setFileDetector(new LocalFileDetector());
            driver.manage()
                    .window()
                    .setSize(new Dimension(1920, 1080));
        }
    }
}
