package ru.netology;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.getRandomLogin;
import static ru.netology.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {

        val registeredUser = getRegisteredUser("active");
        $("[data-test-id=\"login\"] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(registeredUser.getPassword());
        $("[role=\"button\"][data-test-id=\"action-login\"]").click();
        $(withText("Личный кабинет")).shouldBe(visible, Duration.ofSeconds(10));

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        $("[data-test-id=\"login\"] .input__control").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(DataGenerator.getRandomPassword());
        $("[role=\"button\"][data-test-id=\"action-login\"]").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        val blockedUser = getRegisteredUser("blocked");
        $("[data-test-id=\"login\"] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(blockedUser.getPassword());
        $("[role=\"button\"][data-test-id=\"action-login\"]").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(10));

    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        val registeredUser = getRegisteredUser("active");
        val wrongLogin = getRandomLogin();
        $("[data-test-id=\"login\"] .input__control").setValue(wrongLogin);
        $("[data-test-id=\"password\"] .input__control").setValue(registeredUser.getPassword());
        $("[role=\"button\"][data-test-id=\"action-login\"]").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        val registeredUser = getRegisteredUser("active");
        val wrongPassword = getRandomPassword();
        $("[data-test-id=\"login\"] .input__control").setValue(registeredUser.getLogin());
        $("[data-test-id=\"password\"] .input__control").setValue(wrongPassword);
        $("[role=\"button\"][data-test-id=\"action-login\"]").click();
        $(withText("Ошибка")).shouldBe(visible, Duration.ofSeconds(10));
    }
}

