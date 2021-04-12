package steps;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;

import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BaseSteps {
    @Step("Открываем главную страницу")
    public void openMainPage() {
        open("");
    }

    @Step("Кликаем на тег {tagName}")
    public void goToTag(String tagName) {
        $(".tags").$(byText(tagName)).click();
    }

    @Step("Проверяем, что заголовок открывшейся страницы содержит тег {tagName}")
    public void checkTitleTag(String tagName) {
        $(".page-title").shouldHave(text("Products tagged with '" + tagName + "'"));
    }

    @Step("Переходим на страницу случайного товара, отсортированного по тегу")
    public void goToRandomProduct() {
        ElementsCollection products = $$(".product-item");
        products.get(new Random().nextInt(products.size())).$(".picture").click();
    }

    @Step("Проверяем, что на странице товара есть тег {tagName}")
    public void checkProductTag(String tagName) {
        $(".product-tags-box").shouldHave(text(tagName));
    }
}
