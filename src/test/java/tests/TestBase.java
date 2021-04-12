package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static helpers.AttachmentHelper.*;

public class TestBase {
    @BeforeAll
    public static void configureDriver() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com/";
        RestAssured.filters(new AllureRestAssured());
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://demowebshop.tricentis.com/";
    }

    @AfterEach
    public void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console log", getConsoleLog());
        closeWebDriver();
    }
}
