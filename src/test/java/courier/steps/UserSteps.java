package courier.steps;

import courier.models.Courier;
import courier.models.CreateCourier;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserSteps {

    public final String BASE_URI = "http://qa-scooter.praktikum-services.ru";
    public final String ROOT = "/api/v1/courier";


    @Step ("create courier")
    public ValidatableResponse createCourier(CreateCourier user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(ROOT)
                .then();
    }

    @Step ("login courier")
    public ValidatableResponse loginUser(Courier user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body(user)
                .when()
                .post(ROOT + "/login")
                .then();
    }


    public int loginUserGetID(Courier user) {
        return loginUser(user).extract().path("id");
    }

    @Step ("delete courier")
    public void deleteUser(int id) {
        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .when()
                .delete(ROOT + "/" + id)
                .then();
    }


}
