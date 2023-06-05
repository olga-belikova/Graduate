package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.OrderPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class GraduateTest {

    OrderPage orderPage;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        orderPage = open("http://localhost:8080/", OrderPage.class);
    }


    @Test
    @DisplayName("Успешный платеж")
    void shouldSuccessfulPayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.successfulPayment(cardInfo);
    }

    @Test
    @DisplayName("Успешный кредит")
    void shouldSuccessfulCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.successfulCredit(cardInfo);
    }

    @Test
    @DisplayName("Платеж отклонен")
    void shouldFailPayment() {
        var cardInfo = DataHelper.getInvalidCard();
        orderPage.failPayment(cardInfo);
    }

    @Test
    @DisplayName("Кредит отклонен")
    void shouldFailCredit() {
        var cardInfo = DataHelper.getInvalidCard();
        orderPage.failCredit(cardInfo);
    }

    @Test
    @DisplayName("Пустая форма покупки")
    void shouldNotifyIfEmptyFieldsPayment() {
        orderPage.emptyFieldsPayment();
    }

    @Test
    @DisplayName("Пустая форма кредита")
    void shouldNotifyIfEmptyFieldsCredit() {
        orderPage.emptyFieldsCredit();
    }

    @Test
    @DisplayName("Невалидное значение Месяца: 00")
    void shouldNotifyIfInvalidMonthBelow() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidMonthBelow(cardInfo);
    }

    @Test
    @DisplayName("Невалидное значение Месяца: 13")
    void shouldNotifyIfInvalidMonthAbove() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidMonthAbove(cardInfo);
    }
}
