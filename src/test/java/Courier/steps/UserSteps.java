package Courier.steps;

import Courier.models.Courier;
import Courier.models.CreateCourier;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps {

    public final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    public final String ROOT = "/api/v1/courier";


    public ValidatableResponse CreateCourier(CreateCourier user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(ROOT)
                .then();
    }

    public ValidatableResponse LoginUser(Courier user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(ROOT + "/login")
                .then();
    }

    public int LoginUserGetID(Courier user) {
        return LoginUser(user).extract().path("id");
    }

    public void DeleteUser(int id) {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .delete(ROOT + "/" + id)
                .then();
    }


}
