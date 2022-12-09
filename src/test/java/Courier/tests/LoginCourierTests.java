package Courier.tests;

import Courier.models.Courier;
import Courier.models.CourierGeneration;
import Courier.models.CreateCourier;
import Courier.steps.UserSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests {

    UserSteps step = new UserSteps();
    CourierGeneration generation = new CourierGeneration();
    private int courierId;


    @After
    public void deleteCourier() {
        if (courierId > 0) {
            step.DeleteUser(courierId);
        }
    }

    @Test
    @DisplayName("Авторизация курьера - позитивный тест")
    @Description("Проверка успешного авторизации курьера")
    public void LoginSuccess() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());

        step.CreateCourier(createCourier);

        step.LoginUser(courierCredentials)
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());

        courierId = step.LoginUserGetID(courierCredentials);
    }


    @Test
    @DisplayName("Авторизация курьера - без поля login")
    public void LoginWithOutLogin() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(null, createCourier.getPassword());

        step.CreateCourier(createCourier);

        step.LoginUser(courierCredentials)
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierCredentials.setLogin(createCourier.getLogin());
        courierId = step.LoginUserGetID(courierCredentials);

    }

    @Test
    @DisplayName("Авторизация курьера - без поля password")
    public void LoginWithOutPassword() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), null);

        step.CreateCourier(createCourier);

        step.LoginUser(courierCredentials)
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        courierCredentials.setPassword(createCourier.getPassword());
        courierId = step.LoginUserGetID(courierCredentials);
    }

    @Test
    @DisplayName("Авторизация курьера - Не существующий логин")
    public void LoginWithUnknownLogin() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());

        step.LoginUser(courierCredentials)
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера - Не корректный пароль ")
    public void LoginWithIncorrectPassword() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getIncorrectPassword());
        step.CreateCourier(createCourier);

        step.LoginUser(courierCredentials)
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));


        courierCredentials.setPassword(createCourier.getPassword());
        courierId = step.LoginUserGetID(courierCredentials);
    }

}
