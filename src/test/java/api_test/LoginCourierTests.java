package api_test;

import api_test.clients.CourierClient;
import api_test.models.CreateCourier;
import api_test.models.LoginCourier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api_test.utils.RandomUtils;

import static org.hamcrest.Matchers.*;

public class LoginCourierTests {
    private CourierClient courierClient;
    private String login;
    private String password;
    private String firstName;
    private String courierId;

    @Before
    @Step("Подготовка тестовых данных - создание курьера")
    public void setUp() {
        courierClient = new CourierClient();
        login = RandomUtils.getRandomLogin();
        password = RandomUtils.getRandomPassword();
        firstName = RandomUtils.getRandomFirstName();

        courierClient.createCourier(new CreateCourier(login, password, firstName));
    }

    @After
    @Step("Удаление тестового курьера")
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void courierCanLoginSuccessfully() {
        Response response = loginCourier(login, password);
        verifyLoginSuccess(response);
        saveCourierId(response);
    }

    @Test
    @DisplayName("Авторизация с неверными учетными данными")
    public void loginWithInvalidCredentialsFails() {
        loginWithInvalidCredentials("wrong_login", "wrong_password");
        verifyInvalidCredentialsError();
    }

    @Test
    @DisplayName("Авторизация без обязательного поля")
    public void loginWithoutRequiredFieldFails() {
        loginWithMissingField(login, "");
        verifyMissingFieldError();
    }

    @Step("Авторизация курьера")
    private Response loginCourier(String login, String password) {
        return courierClient.loginCourier(new LoginCourier(login, password));
    }

    @Step("Проверка успешной авторизации")
    private void verifyLoginSuccess(Response response) {
        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Step("Сохранение ID курьера")
    private void saveCourierId(Response response) {
        courierId = response.path("id").toString();
    }

    @Step("Авторизация с неверными учетными данными")
    private void loginWithInvalidCredentials(String login, String password) {
        courierClient.loginCourier(new LoginCourier(login, password));
    }

    @Step("Проверка ошибки при неверных учетных данных")
    private void verifyInvalidCredentialsError() {
        courierClient.loginCourier(new LoginCourier("wrong_login", "wrong_password"))
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Авторизация с отсутствующим полем")
    private void loginWithMissingField(String login, String password) {
        courierClient.loginCourier(new LoginCourier(login, password));
    }

    @Step("Проверка ошибки при отсутствии обязательного поля")
    private void verifyMissingFieldError() {
        courierClient.loginCourier(new LoginCourier(login, ""))
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
