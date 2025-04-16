package clients;

import config.ApiConfig;
import models.CreateCourier;
import models.LoginCourier;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierClient {
    public Response createCourier(CreateCourier createCourier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(ApiConfig.BASE_URL)
                .body(createCourier)
                .when()
                .post(ApiConfig.CREATE_COURIER);
    }

    public Response loginCourier(LoginCourier loginCourier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(ApiConfig.BASE_URL)
                .body(loginCourier)
                .when()
                .post(ApiConfig.LOGIN_COURIER);
    }

    public void deleteCourier(String id) {
        given()
                .header("Content-type", "application/json")
                .baseUri(ApiConfig.BASE_URL)
                .when()
                .delete(ApiConfig.CREATE_COURIER + "/" + id);
    }
}
