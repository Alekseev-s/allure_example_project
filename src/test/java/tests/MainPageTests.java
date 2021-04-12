package tests;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.BaseSteps;

import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Epic("Some Epic")
@Feature("Тесты главной страницы")
public class MainPageTests extends TestBase {
    String tagName = "camera";
    BaseSteps steps = new BaseSteps();

    @Test
    @Story("Тесты на сортировку по тегам")
    @DisplayName("Товары соответствуют выбранному тегу (lambda steps)")
    void filterByTagLambdaStepsTest() {
        step("Открываем главную страницу", () -> {
            open("");
        });

        step("Кликаем на тег " + tagName, () -> {
            $(".tags").$(byText(tagName)).click();
        });

        step("Проверяем, что заголовок открывшейся страницы содержит тег " + tagName, () -> {
            $(".page-title").shouldHave(text("Products tagged with '" + tagName + "'"));
        });

        step("Переходим на страницу случайного товара, отсортированного по тегу", () -> {
            ElementsCollection products = $$(".product-item");
            products.get(new Random().nextInt(products.size())).$(".picture").click();
        });

        step("Проверяем, что на странице товара есть тег " + tagName, () -> {
            $(".product-tags-box").shouldHave(text(tagName));
        });
    }

    @Test
    @Story("Тесты на сортировку по тегам")
    @DisplayName("Товары соответствуют выбранному тегу (method steps)")
    void filterByTagMethodStepsTest() {
        steps.openMainPage();
        steps.goToTag("camera");
        steps.checkTitleTag("camera");
        steps.goToRandomProduct();
        steps.checkProductTag("camera");
    }

    @Test
    @Story("Тесты на поиск товаров")
    @DisplayName("Проверка поиска товаров по части наименования")
    void searchProductTest() {
        open("");
        $("#small-searchterms").setValue("Diamond");
        $("#small-searchterms").sibling(0).click();
        $$(".product-item").forEach(i -> i.$(".product-title").shouldHave(text("Diamond")));
    }

    @Test
    @Story("Тесты на недавно посещенные товары")
    @DisplayName("Последний просмотренный товар должен отображаться в списке недавно посещенных")
    void recentlyViewedTest() {
        open("25-virtual-gift-card");
        open("");
        $(".block-recently-viewed-products").$(".product-name").shouldHave(text("$25 Virtual Gift Card"));
    }
}
