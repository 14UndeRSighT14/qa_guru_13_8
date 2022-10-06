package github;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideRepositoryTest extends BaseTest{

    @Test
    void findSoftAssertionsAndCheckCodJUNIT(){

        // 1. Открыть страницу Selenide в Github
        open("https://github.com/selenide/selenide");
        // Проверить, что в хидере в ссылке есть текст "selenide"
        $("#repository-container-header").$("a").shouldHave(text("selenide"));

        // 2. Нажать на вкладку "Wiki"
        $("#wiki-tab").click();
        // Проверить, что перешли на вкладку "Wiki" - есть текст "Welcome to the selenide wiki!"
        $("#user-content-welcome-to-the-selenide-wiki").parent().shouldHave(text("Welcome to the selenide wiki!"));

        // 3. Убедитесься, что в списке страниц (Pages) есть страница SoftAssertions
        $("#wiki-body").$$(".internal.present").shouldHave(itemWithText("Soft assertions"));

        // 4. Открыть страницу SoftAssertions
        $("#wiki-body").$$(".internal.present").findBy(text("Soft assertions")).click();
        // Проверить, что перешли открыта страница "SoftAssertions"
        $("#wiki-wrapper").$$("div").get(0).shouldHave(text("SoftAssertions"));

        // 5. Убедитесься, что на странице "SoftAssertions" есть пример кода для JUnit5
        $("#wiki-body").$$("div").get(0).$$("h4").get(2).shouldHave(text("JUnit5"));


    }
}