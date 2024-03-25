package extensions;


import config.AuthConfig;
import io.restassured.response.Response;
import models.LoginResponseDataLombokModel;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;

public class LoginExtension implements BeforeEachCallback {
    AuthConfig authConfig = ConfigFactory.create(AuthConfig.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        LoginResponseDataLombokModel authData = new LoginResponseDataLombokModel();
        authData.setUserName(authConfig.userLogin());
        authData.setPassword(authConfig.password());
        Response response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(loginResponseSpec)
                        .extract().response());
        String userId = response.path("userId"),
                token = response.path("token"),
                expires = response.path("expires");
        step("Open selenoid", () -> {
            open("/favicon.ico");
        });
        step("Get WebDriver", () -> {
            getWebDriver().manage().addCookie(new Cookie("userID", userId));
            getWebDriver().manage().addCookie(new Cookie("token", token));
            getWebDriver().manage().addCookie(new Cookie("expires", expires));
        });
    }
}
