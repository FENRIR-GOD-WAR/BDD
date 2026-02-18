package ru.netology.web.page;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id=login] input");
    private final SelenideElement passwordField =$("[data-test-id=password] input");
    private final SelenideElement loginButton = $("[data-test-id=action-login]");
    private final SelenideElement errorMessage = $("[data-test-id=error]");

    public VerificationPage validLogin(DataHelper.AuthInfo Info) {
        login(Info);
        return new VerificationPage();
    }

    public void login(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public void checkErrorMassage() {
        errorMessage.should(Condition.visible).should(Condition.text("Ошибка"));
    }
}