package tests.qa;


import extensions.WithAuthorization;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBaseAPI;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;


public class demoQaTests extends TestBaseAPI {

    @Owner("iStarzG")
    @DisplayName("Checking the functionality of the logout button")
    @Test
    @WithAuthorization
    void successfulLogoutTest() {
        step("Open browser and logout", () -> {
            open("/books");
            $(byText("Log out")).click();
            $("#userForm").shouldBe(visible);
        });
        step("Checking that the login window opens", () -> {
            assertThat(url()).contains("/login");
        });
    }
}


