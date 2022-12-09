package Order.steps;

import Order.models.CreateOrder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    public final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    public final String ROOT = "/api/v1/orders";

    public ValidatableResponse GetOrderList() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .get(ROOT)
                .then();
    }

    public ValidatableResponse CreateOrder(CreateOrder order) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(order)
                .when()
                .post(ROOT)
                .then();
    }

}
