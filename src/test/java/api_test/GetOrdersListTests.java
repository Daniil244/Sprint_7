package api_test;

import api_test.clients.OrderClient;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class GetOrdersListTests {
    private OrderClient orderClient;

    @Before
    @Step("Инициализация клиента для работы с заказами")
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test 
    @DisplayName("Получение списка заказов")
    public void getOrdersListSuccessfully() {
        getOrdersList();
        verifyOrdersListNotEmpty();
    }

    @Step("Запрос списка заказов")
    private void getOrdersList() {
        orderClient.getOrdersList();
    }

    @Step("Проверка, что список заказов не пустой")
    private void verifyOrdersListNotEmpty() {
        orderClient.getOrdersList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
