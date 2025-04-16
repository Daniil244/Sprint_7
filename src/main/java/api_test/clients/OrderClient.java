package clients;

import config.ApiConfig;
import io.restassured.response.Response;
import models.CreateOrder;

import static io.restassured.RestAssured.given;

public class OrderClient {
    public Response createOrder(CreateOrder createOrder) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(ApiConfig.BASE_URL)
                .body(createOrder)
                .when()
                .post(ApiConfig.CREATE_ORDER);
    }

    public Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(ApiConfig.BASE_URL)
                .when()
                .get(ApiConfig.CREATE_ORDER);
    }

    public Response cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(ApiConfig.BASE_URL)
                .body("{\"track\": " + track + "}")
                .when()
                .put(ApiConfig.CREATE_ORDER + "/cancel");
    }
}
