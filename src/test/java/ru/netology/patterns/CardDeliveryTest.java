package ru.netology.patterns;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private Faker faker;
    private CardDeliveryInfo cardDeliveryInfo;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
        cardDeliveryInfo = new CardDeliveryInfo(
                faker.address().cityName(),
                TestUtil.getLastNameAndName(faker.name()),
                TestUtil.getPhone(faker.phoneNumber()),
                LocalDate.now().plusDays(4)
        );
    }

    @Test
    public void cardDeliverySuccess() {
        open("http://0.0.0.0:9999");
        $("[data-test-id='city'] .input__control")
                .setValue(cardDeliveryInfo.getCity());
        $("div.input__popup .input__menu .menu-item__control")
                .shouldHave(Condition.exactText(cardDeliveryInfo.getCity()))
                .click();
        setDate(cardDeliveryInfo.getDate());
        $("[data-test-id='name'] .input__control")
                .setValue(cardDeliveryInfo.getName());
        $("[data-test-id='phone'] .input__control")
                .setValue(cardDeliveryInfo.getPhone());


        $("[data-test-id='agreement'] span")
                .click();
        $("button.button")
                .click();
        checkSuccessNotification(cardDeliveryInfo.getDate());

        LocalDate newDate = cardDeliveryInfo.getDate().plusDays(1);
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

    private void checkSuccessNotification(LocalDate date) {
        $("[data-test-id='success-notification'] .notification__title").shouldBe(
                Condition.visible
        )
                .shouldHave(Condition.exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(Condition.visible)
                .shouldHave(
                        Condition.exactText("Встреча успешно запланирована на " + TestUtil.format(
                                date)
                        )
                );
    }

    private void setDate(LocalDate date) {
        $("[data-test-id='date'] span input").click();
        $("[data-test-id='date'] span input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String dateString = TestUtil.format(date);
        $("[data-test-id='date'] span input").setValue(dateString);
        $("[data-test-id='date'] span").click();
    }

}
