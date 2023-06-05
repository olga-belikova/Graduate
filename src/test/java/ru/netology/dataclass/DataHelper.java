package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    private static Faker faker = new Faker(new Locale("en"));

    @Value
    public static class ValidCard {
        String number;
        String month;
        String year;
        String name;
        String code;
    }

    @Value
    public static class InvalidCard {
        String number;
        String month;
        String year;
        String name;
        String code;
    }

    public static ValidCard getValidCard() {
        return new ValidCard(getValidNumber(), getRandomMonth(), getRandomYear(), getRandomName(), getRandomCode());
    }

    public static InvalidCard getInvalidCard() {
        return new InvalidCard(getInvalidNumber(), getRandomMonth(), getRandomYear(), getRandomName(), getRandomCode());
    }

    private static String getValidNumber() { return "4444 4444 4444 4441"; }
    private static String getInvalidNumber() { return "4444 4444 4444 4442"; }

    public static String getRandomMonth() {
        var month = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        return month[new Random().nextInt(month.length)];
    }

    public static String getRandomYear() {
        var year = new String[]{"23", "24", "25", "26", "27", "28"};
        return year[new Random().nextInt(year.length)];
    }

    private static String getRandomName() {
        return faker.name().fullName();
    }

    private static String getRandomCode() {
        return faker.numerify("###");
    }

}
