package ru.netology.patterns;

import com.github.javafaker.Name;
import com.github.javafaker.PhoneNumber;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class TestUtil {

    public static final String SPACE = " ";
    public static final String LEFT_BRACKET = "(";
    public static final String RIGHT_BRACKET = ")";
    public static final String HYPHEN = "-";
    public static final String EMPTY_STR = "";
    public static final String DATE_FORMAT = "dd.MM.yyyy";

    private TestUtil() {

    }

    public static String getLastNameAndName(Name name){
        return name.lastName() + SPACE + name.firstName();
    }

    public static String getPhone(PhoneNumber phoneNumber) {
        return phoneNumber.phoneNumber()
                .replace(LEFT_BRACKET, EMPTY_STR)
                .replace(RIGHT_BRACKET, EMPTY_STR)
                .replace(HYPHEN, EMPTY_STR);
    }

    public static String format(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String getRepeatableKeySequence(Keys key, int count){
        List<CharSequence> keysList = new ArrayList<>();
        for (int i = 0; i < count; i++){
            keysList.add(key);
        }
        return Keys.chord(keysList);
    }

}
