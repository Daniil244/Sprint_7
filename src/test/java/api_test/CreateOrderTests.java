package api_test;

import api_test.clients.OrderClient;
import api_test.models.CreateOrder;
import api_test.models.Order;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    private OrderClient orderClient;
    private int track;
    private final List<String> color;

    public CreateOrderTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {null}
        });
    }

    @Before
    @Step("Инициализация клиента для работы с заказами")
    public void setUp() {
        orderClient = new OrderClient();
    }

    @After
    @Step("Отмена тестового заказа")
    public void tearDown() {
        if (track != 0) {
            orderClient.cancelOrder(track);
        }
    }

    @Test
    @DisplayName("Создание заказа с разными комбинациями цветов")
    public void orderCanBeCreatedWithDifferentColors() {
        CreateOrder request = createOrderRequest();
        Response response = createOrder(request);
        verifyOrderCreatedSuccessfully(response);
        saveTrackNumber(response);
    }

    @Step("Создание тестового заказа")
    private CreateOrder createOrderRequest() {
        return new CreateOrder(
                "Вася",
                "Яснеслася",
                "Сочи, ул. Пляжная, 1",
                "4",
                "+79999999999",
                5,
                "2025-04-12",
                "Побыстрее",
                color
        );
    }

    @Step("Отправка запроса на создание заказа")
    private Response createOrder(CreateOrder request) {
        return orderClient.createOrder(request);
    }

    @Step("Проверка успешного создания заказа")
    private void verifyOrderCreatedSuccessfully(Response response) {
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Сохранение номера трека заказа")
    private void saveTrackNumber(Response response) {
        Order orderDto = response.as(Order.class);
        track = orderDto.getTrack();
    }
}
