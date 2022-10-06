package selenide;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.io.*;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// this is not a full list, just the most common
public class Snippets {

    // Команды браузера
    void browser_command_examples() {

        open("https://google.com");
        open("/customer/orders");     // -Dselenide.baseUrl=http://123.23.23.1
        open("/", AuthenticationType.BASIC,
                new BasicAuthCredentials("user", "password")); // Для Basic Auth

        Selenide.back(); // Нажатие на кнопку "Назад" в браузере
        Selenide.refresh(); // Нажатие на кнопку  "Обновить" в браузере

        Selenide.clearBrowserCookies(); // Удаление куки в браузере
        Selenide.clearBrowserLocalStorage(); // Удаление локалсторадж в браузере
        executeJavaScript("sessionStorage.clear();"); // no Selenide command for this yet. Удаление сессионсторадж в браузере

        Selenide.confirm(); // OK in alert dialogs
        Selenide.dismiss(); // Cancel in alert dialogs

        Selenide.closeWindow(); // close active tab, закрывает активную вкладку в браузере
        Selenide.closeWebDriver(); // close browser completely, закрывает браузер

        Selenide.switchTo().frame("new"); // Переход во frame для того, чтобы увидеть элементы дом-модели frame
        Selenide.switchTo().defaultContent(); // Переход из frame в дом-модели самой страницы

        Selenide.switchTo().window("The Internet"); // Переход на нужную вкладку в браузере
    }

    // Команды для выбора элемента
    void selectors_examples() {
        $("div").click(); // Находит первый элемент "div" и нажимает
        element("div").click(); // Находит первый элемент "div" и нажимает (для Котлин в основном, так как "$" там зарезервирован)

        $("div", 2).click(); // Находит третий элемент "div" и нажимает (отчет index с 0)

        $x("//h1/div").click(); // Находит элемент по xpath и нажимает (используют обычно в тех случаях, если при помощи других команд элемент долго ищется)
        $(byXpath("//h1/div")).click(); // Находит элемент по xpath и нажимает (используют обычно в тех случаях, если при помощи других команд элемент долго ищется)

        $(byText("full text")).click(); // Ищет элемент по полному вхождения текста по всей дом-модели и нажимает
        $(withText("ull tex")).click(); // Ищет элемент по частичному вхождения текста по всей дом-модели и нажимает

        $(byTagAndText("div","full text")); // Ищет элемент по полному вхождения текста внутри "div" и нажимает
        $(withTagAndText("div","ull text")); // Ищет элемент по частичному вхождения текста внутри "div" и нажимает

        $("").parent(); // Подняться на уровень выше в DOM
        $("").sibling(1); // братья/сестры вниз на том же уровне DOM
        $("").preceding(1); // братья/сестры вверх на том же уровне DOM
        $("").closest("div"); // Подняться на уровень выше в DOM к конкретному элементу
        $("").ancestor("div"); // Синоним closest
        $("div:last-child"); // Для уточнения нужного div

        $("div").$("h1").find(byText("abc")).click(); // Внутри div ищем h1, а внутри h1 ищем техт "abc" и нажимаем

        // very optional
        $(byAttribute("abc", "x")).click(); // Поиск по атрибуту элемента
        $("[abc=x]").click(); // Поиск по атрибуту элемента

        $(byId("mytext")).click(); // Поиск по id элемента
        $("#mytext").click(); // Поиск по id элемента

        $(byClassName("red")).click(); // Поиск по классу элемента
        $(".red").click(); // Поиск по классу элемента
    }

    // Действия над элементами
    void actions_examples() {
        $("").click(); // Клик по элементу левой клавишей мыши
        $("").doubleClick(); // Двойной клик по элементу левой клавишей мыши
        $("").contextClick(); // Клик по элементу правой клавишей мыши

        $("").hover(); // Навести мышку на элемент

        $("").setValue("text"); // Внести текст в поле (удаляет предыдущий текст)
        $("").append("text"); // Добавить текст в поле (если в поле что-то введено было до этого)
        $("").clear(); // Очищает поле
        $("").setValue(""); // Очищает поле

        $("div").sendKeys("c"); // Нажать клавишу "с" для элемента "div"
        actions().sendKeys("c").perform(); // Нажать клавишу "с" для всего приложения
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform(); // Нажать сочетания клавиш "ctrl" + "f" для всего приложения
        $("html").sendKeys(Keys.chord(Keys.CONTROL, "f")); // Нажать сочетания клавиш "ctrl" + "f" для элемента html

        $("").pressEnter(); // Нажать клавишу Enter
        $("").pressEscape(); // Нажать клавишу Escape
        $("").pressTab(); // Нажать клавишу Tab


        // complex actions with keybord and mouse, example
        actions().moveToElement($("div")).clickAndHold().moveByOffset(300, 200).release().perform(); // Навести мышку на элемент "div" и нажать левую клавишу мыши,  и не отпуска. перенести на выбранную область


        // old html actions don't work with many modern frameworks
        $("").selectOption("dropdown_option"); // выбор нужной строки из списка (для некастомных списков)
        $("").selectRadio("radio_options"); // выбор нужной радио-кнопки (для некастомных радио-кнопок)

    }

    // Проверки для элементов, стандартный таймаут - 4 секунды
    void assertions_examples() {
        $("").shouldBe(visible); // нужный элемент видимый
        $("").shouldNotBe(visible); // нужный элемент невидимый
        $("").shouldHave(text("abc")); // нужный элемент содрежит текст
        $("").shouldNotHave(text("abc")); // нужный элемент не содрежит текст
        $("").should(appear); // нужный элемент появился
        $("").shouldNot(appear); // нужный элемент не появился


        //longer timeouts
        $("").shouldBe(visible, Duration.ofSeconds(30)); // можно менять таймаут


    }

    void conditions_examples() {
        $("").shouldBe(visible); // нужный элемент видимый
        $("").shouldBe(hidden); // нужный элемент исчез

        $("").shouldHave(text("abc")); // поиск по вхождению без учета регистра
        $("").shouldHave(exactText("abc")); // поиск по точному вхождению без учета регистра
        $("").shouldHave(textCaseSensitive("abc")); // поиск по вхождению с учетом регистра
        $("").shouldHave(exactTextCaseSensitive("abc")); // поиск по точному вхождению с учетом регистра
        $("").should(matchText("[0-9]abc$")); // поиск по регулярному выражению

        $("").shouldHave(cssClass("red")); // элемент содержит нужный класс
        $("").shouldHave(cssClass("red"), cssClass("red2")); // элемент содержит 2 нужных классов
        $("").shouldHave(cssValue("font-size", "12")); // в нужном элементе атрибут CSS "font-size" = 12

        $("").shouldHave(value("25")); // input элемент содержит текст, которой является частью любого текста
        $("").shouldHave(exactValue("25")); // input элемент содержит текст, которой является полной частью любого текста
        $("").shouldBe(empty); // Элемент пустой

        $("").shouldHave(attribute("disabled")); // у элемента есть атрибут "disabled"
        $("").shouldHave(attribute("name", "example")); // у элемента есть атрибут "name" со значением "example"
        $("").shouldHave(attributeMatching("name", "[0-9]abc$")); // у элемента есть атрибут "name" со значением из регулярного выражения

        $("").shouldBe(checked); // for checkboxes, проверяет, что чекбокс отмечен
        $("").shouldNotBe(checked); // for checkboxes, проверяет, что чекбокс не отмечен

        // Warning! Only checks if it is in DOM, not if it is visible! You don't need it in most tests!
        $("").should(exist); // проверяет, что элемент существует, и он необязательно видимый

        // Warning! Checks only the "disabled" attribute! Will not work with many modern frameworks
        $("").shouldBe(disabled); // для атрибутов
        $("").shouldBe(enabled);  // для атрибутов
    }

    // Команды коллекции
    void collections_examples() {

        $$("div"); // does nothing!

        // selections
        $$("div").filterBy(text("123")).shouldHave(size(1));
        $$("div").excludeWith(text("123")).shouldHave(size(1));

        $$("div").first().click();
        elements("div").first().click();
        // $("div").click();
        $$("div").last().click();
        $$("div").get(1).click(); // the second! (start with 0)
        $("div", 1).click(); // same as previous
        $$("div").findBy(text("123")).click(); //  finds first

        // assertions
        $$("").shouldHave(size(0));
        $$("").shouldBe(CollectionCondition.empty); // the same

        $$("").shouldHave(texts("Alfa", "Beta", "Gamma"));
        $$("").shouldHave(exactTexts("Alfa", "Beta", "Gamma"));

        $$("").shouldHave(textsInAnyOrder("Beta", "Gamma", "Alfa"));
        $$("").shouldHave(exactTextsCaseSensitiveInAnyOrder("Beta", "Gamma", "Alfa"));

        $$("").shouldHave(itemWithText("Gamma")); // only one text

        $$("").shouldHave(sizeGreaterThan(0));
        $$("").shouldHave(sizeGreaterThanOrEqual(1));
        $$("").shouldHave(sizeLessThan(3));
        $$("").shouldHave(sizeLessThanOrEqual(2));


    }

    void file_operation_examples() throws FileNotFoundException {

        File file1 = $("a.fileLink").download(); // only for <a href=".."> links
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER)); // more common options, but may have problems with Grid/Selenoid

        File file = new File("src/test/resources/readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        // don't forget to submit!
        $("uploadButton").click();
    }

    void javascript_examples() {
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0]+arguments[1])", "abc", 12);
        long fortytwo = executeJavaScript("return arguments[0]*arguments[1];", 6, 7);

    }
}