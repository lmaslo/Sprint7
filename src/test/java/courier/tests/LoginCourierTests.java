package courier.tests;

import courier.models.Courier;
import courier.models.CourierGeneration;
import courier.models.CreateCourier;
import courier.steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests {

    UserSteps step = new UserSteps();
    CourierGeneration generation = new CourierGeneration();
    private int courierId;


    @After
    public void deleteCourier() {
        if (courierId > 0) {
            step.deleteUser(courierId);
        }
    }

    @Test
    @DisplayName("Авторизация курьера - позитивный тест")
    @Description("Проверка успешного авторизации курьера")
    public void loginSuccess() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());

        step.createCourier(createCourier);

        step.loginUser(courierCredentials)
                .log().all()
                .statusCode(SC_OK)
                .body("id", notNullValue());

        courierId = step.loginUserGetID(courierCredentials);
    }


    @Test
    @DisplayName("Авторизация курьера - без поля login")
    public void loginWithOutLogin() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(null, createCourier.getPassword());

        step.createCourier(createCourier);

        step.loginUser(courierCredentials)
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierCredentials.setLogin(createCourier.getLogin());
        courierId = step.loginUserGetID(courierCredentials);

    }

    @Test
    @DisplayName("Авторизация курьера - без поля password")
    public void loginWithOutPassword() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), null);

        step.createCourier(createCourier);

        step.loginUser(courierCredentials)
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierCredentials.setPassword(createCourier.getPassword());
        courierId = step.loginUserGetID(courierCredentials);
    }

    @Test
    @DisplayName("Авторизация курьера - Не существующий логин")
    public void loginWithUnknownLogin() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());

        step.loginUser(courierCredentials)
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера - Не корректный пароль ")
    public void loginWithIncorrectPassword() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getIncorrectPassword());
        step.createCourier(createCourier);

        step.loginUser(courierCredentials)
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));


        courierCredentials.setPassword(createCourier.getPassword());
        courierId = step.loginUserGetID(courierCredentials);
    }

}
