package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import java.util.Map;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;

@Epic("Some Epic")
@Feature("Тесты корзины")
public class CartTests extends TestBase {
    @Test
    @Story("Тесты на добавление товара в корзину")
    @DisplayName("Проверка добавления одного товара в корзину")
    @Description("Некое тестовое описание")
    @Owner("Alekseev-s")
    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "DemoWebShop", url = "http://demowebshop.tricentis.com/")
    void addProductToCartTest() {
        open("smartphone");
        $("[value='Add to cart']").click();

        $("#bar-notification")
                .shouldBe(visible)
                .shouldHave(text("The product has been added to your shopping cart"));
        $("#topcartlink").$(".cart-qty").shouldHave(text("(1)"));

        $("#topcartlink").click();
        $(".cart").$(".product").shouldHave(text("Smartphone"));
    }

    @Test
    @Story("Тесты на удаление товара из корзины")
    @DisplayName("Проверка удаления одного товара из корзины")
    void deleteProductFromCartTest() {
        Map<String, String> cookies = given()
                .formParam("product_attribute_80_2_37", "112")
                .formParam("product_attribute_80_1_38", "114")
                .formParam("addtocart_80.EnteredQuantity", "1")
                .when()
                .post("addproducttocart/details/80/1")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().cookies();

        open("Themes/DefaultClean/Content/images/logo.png");
        getWebDriver().manage().addCookie(new Cookie("Nop.customer", cookies.get("Nop.customer")));
        getWebDriver().manage().addCookie(new Cookie("ARRAffinity", cookies.get("ARRAffinity")));
        open("cart");
        $("[name='removefromcart']").click();
        $("[name='updatecart']").click();
        $(".shopping-cart-page").$(".page-body").shouldHave(text("Your Shopping Cart is empty!"));
        $("#topcartlink").$(".cart-qty").shouldHave(text("(0)"));
    }
}
