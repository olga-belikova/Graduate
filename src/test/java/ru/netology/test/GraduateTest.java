package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.*;
import ru.netology.dataclass.DataHelper;
import ru.netology.dataclass.SQLHelper;
import ru.netology.page.OrderPage;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.dataclass.SQLHelper.cleanDatabase;

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

    @AfterEach
   void teardown() {
        cleanDatabase();
    }



    @Test
    @DisplayName("Успешный платеж")
    void shouldSuccessfulPayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.successfulPayment(cardInfo);

        var expectedStatus = "APPROVED";
        var actualStatus = SQLHelper.getStatusPayment();
        Assertions.assertEquals(expectedStatus, actualStatus);

        var expectedID = SQLHelper.getTransactionId();
        var actualID = SQLHelper.getPaymentId();
        Assertions.assertNotNull(expectedID);
        Assertions.assertNotNull(actualID);
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    @DisplayName("Успешный кредит")
    void shouldSuccessfulCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.successfulCredit(cardInfo);

        var expectedStatus = "APPROVED";
        var actualStatus = SQLHelper.getStatusCredit();
        Assertions.assertEquals(expectedStatus, actualStatus);

        var expectedID = SQLHelper.getBankId();
        var actualID = SQLHelper.getCreditId();
        Assertions.assertNotNull(expectedID);
        Assertions.assertNotNull(actualID);
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    @DisplayName("Платеж отклонен")
    void shouldFailPayment() {
        var cardInfo = DataHelper.getInvalidCard();
        orderPage.failPayment(cardInfo);

        var expectedStatus = "DECLINED";
        var actualStatus = SQLHelper.getStatusPayment();
        Assertions.assertEquals(expectedStatus, actualStatus);

        var expectedID = SQLHelper.getTransactionId();
        var actualID = SQLHelper.getPaymentId();
        Assertions.assertNotNull(expectedID);
        Assertions.assertNotNull(actualID);
        Assertions.assertEquals(expectedID, actualID);
    }

    @Test
    @DisplayName("Кредит отклонен")
    void shouldFailCredit() {
        var cardInfo = DataHelper.getInvalidCard();
        orderPage.failCredit(cardInfo);

        var expectedStatus = "DECLINED";
        var actualStatus = SQLHelper.getStatusCredit();
        Assertions.assertEquals(expectedStatus, actualStatus);

        var expectedID = SQLHelper.getBankId();
        var actualID = SQLHelper.getCreditId();
        Assertions.assertNotNull(expectedID);
        Assertions.assertNotNull(actualID);
        Assertions.assertEquals(expectedID, actualID);
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
    @DisplayName("Несуществующая карта - платеж")
    void shouldNotifyIfNotExistCardPayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidNumberPayment(cardInfo);
    }

    @Test
    @DisplayName("Несуществующая карта - кредит")
    void shouldNotifyIfNotExistCardCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidNumberCredit(cardInfo);
    }

    @Test
    @DisplayName("Платеж: Невалидное значение Месяца: 00")
    void shouldNotifyIfInvalidMonthBelowPayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidMonthBelowPayment(cardInfo);
    }

    @Test
    @DisplayName("Платеж: Невалидное значение Месяца: 13")
    void shouldNotifyIfInvalidMonthAbovePayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidMonthAbovePayment(cardInfo);
    }

    @Test
    @DisplayName("Кредит: Невалидное значение Месяца: 00")
    void shouldNotifyIfInvalidMonthBelowCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidMonthBelowCredit(cardInfo);
    }

    @Test
    @DisplayName("Кредит: Невалидное значение Месяца: 13")
    void shouldNotifyIfInvalidMonthAboveCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidMonthAboveCredit(cardInfo);
    }

    @Test
    @DisplayName("Платеж: Невалидное значение Года: 00")
    void shouldNotifyIfInvalidYearBelowPayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidYearBelowPayment(cardInfo);
    }

    @Test
    @DisplayName("Платеж: Невалидное значение Года: 29")
    void shouldNotifyIfInvalidYearAbovePayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidYearAbovePayment(cardInfo);
    }

    @Test
    @DisplayName("Кредит: Невалидное значение Года: 00")
    void shouldNotifyIfInvalidYearBelowCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidYearBelowCredit(cardInfo);
    }

    @Test
    @DisplayName("Кредит: Невалидное значение Года: 29")
    void shouldNotifyIfInvalidYearAboveCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidYearAboveCredit(cardInfo);
    }

    @Test
    @DisplayName("Платеж: Невалидное значение Имени: рус")
    void shouldNotifyIfInvalidNamePayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidNamePayment(cardInfo);
    }

    @Test
    @DisplayName("Кредит: Невалидное значение Имени: рус")
    void shouldNotifyIfInvalidNameCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidNameCredit(cardInfo);
    }

    @Test
    @DisplayName("Платеж: Невалидное значение CVC: 000")
    void shouldNotifyIfInvalidCodePayment() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidCodePayment(cardInfo);
    }

    @Test
    @DisplayName("Кредит: Невалидное значение CVC: 000")
    void shouldNotifyIfInvalidCodeCredit() {
        var cardInfo = DataHelper.getValidCard();
        orderPage.invalidCodeCredit(cardInfo);
    }
}
