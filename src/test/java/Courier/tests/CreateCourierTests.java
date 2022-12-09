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

public class CreateCourierTests {

    UserSteps step = new UserSteps();
    CourierGeneration generation = new CourierGeneration();
    private int courierId;

    @After
    public void deleteCourier(){
        if (courierId >0){
            step.DeleteUser(courierId);
        }
    }


    @Test
    @DisplayName("Создание Курьера - позитивный тест")
    @Description("Проверка успешного создания курьера, после теста данные с id удаляются")
    public void CreateUserTests() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());

        step.CreateCourier(createCourier)
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = step.LoginUserGetID(courierCredentials);

    }


   //по документации текст ошибки немного другой, по заданию не понятно какой нужно.
    @Test
    @DisplayName("Попытка создать дубликат курьера")
    @Description("Проверка, что в ответе возвращается ошибка при попытке создать курьера с дубилатом login")
    public void CreateDuplicateUserTests() {

        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());
        step.CreateCourier(createCourier);

        step.CreateCourier(createCourier)
                .log().all()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierId = step.LoginUserGetID(courierCredentials);

    }


    @Test
    @DisplayName("Создание Курьера - без поля password")
    @Description("Проверка, что возвращается ошибка, если при создании курьера не заполнен пароль")
    public void CreateUserWithoutPasswordTests() {
        CreateCourier createCourier = generation.newCourier();
        createCourier.setPassword(null);

        step.CreateCourier(createCourier)
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание Курьера - без поля login")
    @Description("Проверка, что возвращается ошибка, если при создании курьера не заполнен login")
    public void CreateUserWithoutLoginTests() {
        CreateCourier createCourier = generation.newCourier();
        createCourier.setLogin(null);

        step.CreateCourier(createCourier)
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
}
