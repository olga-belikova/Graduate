package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderPage {
    private ElementsCollection button = $$("button.button");
    private SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private ElementsCollection nameField = $$("input.input__control");
    private SelenideElement cvcField = $("[placeholder='999']");
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private ElementsCollection invalidFormatNotification = $$(".input__sub");

    public void setValidCard(DataHelper.ValidCard info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.get(3).setValue(info.getName());
        cvcField.setValue(info.getCode());
    }

    public void setInvalidCard(DataHelper.InvalidCard info) {
        cardNumberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.get(3).setValue(info.getName());
        cvcField.setValue(info.getCode());
    }

    public void buttonClick(int index) {
        button.get(index).click();
    }

    public void successfulPayment(DataHelper.ValidCard info) {
        buttonClick(0);
        setValidCard(info);
        buttonClick(2);
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void successfulCredit(DataHelper.ValidCard info) {
        buttonClick(1);
        setValidCard(info);
        buttonClick(2);
        successNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void failPayment(DataHelper.InvalidCard info) {
        buttonClick(0);
        setInvalidCard(info);
        buttonClick(2);
        errorNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void failCredit(DataHelper.InvalidCard info) {
        buttonClick(1);
        setInvalidCard(info);
        buttonClick(2);
        errorNotification.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void emptyFieldsPayment() {
        buttonClick(0);
        buttonClick(2);
        invalidFormatNotification.get(0).shouldBe(visible);
        invalidFormatNotification.get(1).shouldBe(visible);
        invalidFormatNotification.get(2).shouldBe(visible);
        invalidFormatNotification.get(3).shouldBe(visible);
        invalidFormatNotification.get(4).shouldBe(visible);
    }

    public void emptyFieldsCredit() {
        buttonClick(1);
        buttonClick(2);
        invalidFormatNotification.get(0).shouldBe(visible);
        invalidFormatNotification.get(1).shouldBe(visible);
        invalidFormatNotification.get(2).shouldBe(visible);
        invalidFormatNotification.get(3).shouldBe(visible);
        invalidFormatNotification.get(4).shouldBe(visible);
    }

    public void invalidMonthBelow(DataHelper.ValidCard info) {
        buttonClick(0);
        cardNumberField.setValue(info.getNumber());
        monthField.setValue("00");
        yearField.setValue(info.getYear());
        nameField.get(3).setValue(info.getName());
        cvcField.setValue(info.getCode());
        buttonClick(2);
        invalidFormatNotification.get(0).shouldBe(visible);
    }

    public void invalidMonthAbove(DataHelper.ValidCard info) {
        buttonClick(0);
        cardNumberField.setValue(info.getNumber());
        monthField.setValue("13");
        yearField.setValue(info.getYear());
        nameField.get(3).setValue(info.getName());
        cvcField.setValue(info.getCode());
        buttonClick(2);
        invalidFormatNotification.get(0).shouldBe(visible);
    }

}
