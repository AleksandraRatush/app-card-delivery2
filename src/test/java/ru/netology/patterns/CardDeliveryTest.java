package ru.netology.patterns;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.patterns.data.DataGenerator;

import java.time.Duration;



import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    private DataGenerator.CardDeliveryInfo cardDeliveryInfo;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearAllDown(){
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        cardDeliveryInfo = DataGenerator.generateCardDeliveryInfo("ru");
    }

    @Test
    public void cardDeliverySuccess() {
        open("http://0.0.0.0:9999");
        $("[data-test-id='city'] .input__control")
                .setValue(cardDeliveryInfo.getCity());
        $("div.input__popup .input__menu .menu-item__control")
                .shouldHave(Condition.exactText(cardDeliveryInfo.getCity()))
                .click();
        String date = DataGenerator.generateDate(4);
        setDate(date);
        $("[data-test-id='name'] .input__control")
                .setValue(cardDeliveryInfo.getName());
        $("[data-test-id='phone'] .input__control")
                .setValue(cardDeliveryInfo.getPhone());


        $("[data-test-id='agreement'] span")
                .click();
        $("button.button")
                .click();
        checkSuccessNotification(date);

        String newDate = DataGenerator.generateDate(5);
        setDate(newDate);
        $("button.button")
                .click();
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(
                Condition.visible, Duration.ofSeconds(1)
        )
                .shouldHave(Condition.exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(
                        Condition.exactText("У вас уже запланирована встреча на другую дату. Перепланировать?\n" +
                                "\n" +
                                "Перепланировать"
                        )
                );
        $("[data-test-id='replan-notification'] .button").click();
        checkSuccessNotification(newDate);

    }

    private void checkSuccessNotification(String date) {
        $("[data-test-id='success-notification'] .notification__title").shouldBe(
                Condition.visible
        )
                .shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(
                        Condition.exactText("Встреча успешно запланирована на " + date
                        )
                );
    }

    private void setDate(String date) {
        $("[data-test-id='date'] span input").click();
        $("[data-test-id='date'] span input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] span input").setValue(date);
        $("[data-test-id='date'] span").click();
    }

}
