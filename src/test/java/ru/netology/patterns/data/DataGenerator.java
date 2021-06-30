package ru.netology.patterns.data;



import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class DataGenerator {

    public static final String SPACE = " ";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String HYPHEN = "-";
    public static final String EMPTY_STR = "";
    public static final String DATE_FORMAT = "dd.MM.yyyy";


    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.address().city();

    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        Name name = faker.name();
        return name.lastName() + SPACE + name.firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber()
                .replace(LEFT_BRACKET, EMPTY_STR)
                .replace(RIGHT_BRACKET, EMPTY_STR)
                .replace(HYPHEN, EMPTY_STR);
    }

    public static CardDeliveryInfo generateCardDeliveryInfo (String locale) {
        return new CardDeliveryInfo(generateCity(locale), generateName(locale), generatePhone(locale));
    }


    @Value
    public static class CardDeliveryInfo {
        String city;
        String name;
        String phone;
    }




}
