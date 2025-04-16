import api_test.clients.CourierClient;
import api_test.models.CreateCourier;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api_test.utils.RandomUtils;

import static org.hamcrest.Matchers.*;

public class CreateCourierTests {
    private CourierClient courierClient;
    private String login;
    private String password;
    private String firstName;
    private String courierId;

    @Before
    @Step("Подготовка необходимых тестовых данных")
    public void setUp() {
        courierClient = new CourierClient();
        login = RandomUtils.getRandomLogin();
        password = RandomUtils.getRandomPassword();
        firstName = RandomUtils.getRandomFirstName();
    }

    @After
    @Step("Удаление тестовых данных после того как пройдет тест")
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void courierCanBeCreatedSuccessfully() {
        createCourier(login, password, firstName);
        verifyCourierCreatedSuccessfully();
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void duplicateCourierCannotBeCreated() {
        createCourier(login, password, firstName);
        tryToCreateDuplicateCourier(login, password, firstName);
        verifyDuplicateCourierError();
    }

    @Test
    @DisplayName("Создание курьера без обязательного поля")
    public void createCourierWithoutRequiredFieldFails() {
        createCourierWithMissingField(login, "", firstName);
        verifyMissingFieldError();
    }

    @Step("Для создания курьера нужно передать в ручку все обязательные поля")
    private void createCourier(String login, String password, String firstName) {
        courierClient.createCourier(new CreateCourier(login, password, firstName));
    }

    @Step("Проверка успешного создания курьера")
    private void verifyCourierCreatedSuccessfully() {
        courierClient.createCourier(new CreateCourier(login, password, firstName))
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Step("Создание пользователя с логином, который уже есть")
    private void tryToCreateDuplicateCourier(String login, String password, String firstName) {
        courierClient.createCourier(new CreateCourier(login, password, firstName));
    }

    @Step("Проверка ошибки при дублировании логина пользователя")
    private void verifyDuplicateCourierError() {
        courierClient.createCourier(new CreateCourier(login, password, firstName))
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Step("Создание курьера с отсутствующим полем")
    private void createCourierWithMissingField(String login, String password, String firstName) {
        courierClient.createCourier(new CreateCourier(login, password, firstName));
    }

    @Step("Проверка ошибки при отсутствии обязательного поля")
    private void verifyMissingFieldError() {
        courierClient.createCourier(new CreateCourier(login, "", firstName))
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}