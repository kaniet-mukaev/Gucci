package com.gucci.helper;
import java.time.Duration;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.*;

public class AlertHelper {

    // Принять alert
    public void acceptAlert() {
        Selenide.confirm(); // Эквивалент accept()
    }

    // Отклонить alert
    public void dismissAlert() {
        Selenide.dismiss(); // Эквивалент dismiss()
    }

    // Проверить наличие alert
    public boolean isAlertPresent() {
        try {
            // Пытаемся переключиться на alert
            switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Принять alert если присутствует
    public void acceptAlertIfPresented() {
        if (isAlertPresent()) {
            acceptAlert();
        }
    }

    // Ожидать появления alert и принять его
    public void waitAndAcceptAlert(Duration timeout) {
        Selenide.Wait().withTimeout(timeout)
                .until(driver -> {
                    try {
                        driver.switchTo().alert();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                });
        acceptAlert();
    }

    // Получить текст alert
    public String getAlertText() {
        return switchTo().alert().getText();
    }

    // Ввести текст в prompt alert
    public void sendTextToAlert(String text) {
        switchTo().alert().sendKeys(text);
    }

    // Комплексный метод: дождаться, получить текст и принять
    public String waitGetTextAndAcceptAlert(Duration timeout) {
        waitAndAcceptAlert(timeout);
        String alertText = getAlertText();
        acceptAlert();
        return alertText;
    }

}
