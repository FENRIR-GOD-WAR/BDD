package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import java.util.HashMap;
import java.util.Map;

public class TestBase {

    @BeforeAll
    static void setupAll() {
        ChromeOptions options = new ChromeOptions();

        // 🔕 Отключаем менеджер паролей и автозаполнение
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("autofill.credit_card_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        // 🔕 Отключаем все уведомления и инфобары
        options.addArguments(
                "--disable-notifications",
                "--disable-infobars",
                "--disable-save-password-bubble",
                "--no-default-browser-check",
                "--no-first-run",
                "--disable-extensions",
                "--disable-popup-blocking",
                "--disable-blink-features=AutomationControlled"
        );

        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.headless = false; // true — если нужно
    }

    protected DashboardPage loginAndGetDashboard() {
        var loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        return verificationPage.validVerify(verificationCode);
    }
}