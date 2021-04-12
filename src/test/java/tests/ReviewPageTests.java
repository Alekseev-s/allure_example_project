package tests;

import api.API;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

@Epic("Some Epic")
@Feature("Тесты станицы отзывов")
public class ReviewPageTests extends TestBase {
    String email = "qa@test.com";
    String password = "qatest";
    String reviewTitle = "Some test title";
    String reviewText = "The best product ever";

    @Test
    @Story("Тесты на добавление отзыва")
    @DisplayName("Нельзя добавить отзыв, если пользователь не залогинен")
    void needLoginToReviewTest() {
        open("productreviews/45");
        $("#AddProductReview_Title").shouldBe(disabled);
        $("#AddProductReview_ReviewText").shouldBe(disabled);
        $("#review-form").$(".validation-summary-errors")
                .shouldBe(visible)
                .shouldHave(text("Only registered users can write reviews"));
    }

    @Test
    @Story("Тесты на добавление отзыва")
    @DisplayName("Проверка добавления отзыва залогиненным пользователем")
    void addReviewTest() {
        Map<String, String> cookies = new API().login(email, password);

        given()
                .formParam("AddProductReview.Title", reviewTitle)
                .formParam("AddProductReview.ReviewText", reviewText)
                .formParam("AddProductReview.Rating", "5")
                .formParam("add-review", "Submit review")
                .cookies(cookies)
                .when()
                .post("productreviews/31")
                .then()
                .log().ifError()
                .statusCode(200);

        open("Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookies.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("NOPCOMMERCE.AUTH", cookies.get("NOPCOMMERCE.AUTH")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookies.get("ARRAffinity")));
        open("productreviews/31");
        $$(".product-review-item")
                .last()
                .scrollTo()
                .$(".review-title")
                .shouldHave(text(reviewTitle));
        $$(".product-review-item")
                .last()
                .$(".review-text")
                .shouldHave(text(reviewText));
    }
}
