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

public class CreateCourierTests {

    UserSteps step = new UserSteps();
    CourierGeneration generation = new CourierGeneration();
    private int courierId;

    @After
    public void deleteCourier(){
        if (courierId >0){
            step.deleteUser(courierId);
        }
    }


    @Test
    @DisplayName("Создание Курьера - позитивный тест")
    @Description("Проверка успешного создания курьера, после теста данные с id удаляются")
    public void createUserTests() {
        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());

        step.createCourier(createCourier)
                .log().all()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

        courierId = step.loginUserGetID(courierCredentials);

    }


   //по документации текст ошибки немного другой, по заданию не понятно какой нужно.
    @Test
    @DisplayName("Попытка создать дубликат курьера")
    @Description("Проверка, что в ответе возвращается ошибка при попытке создать курьера с дубилатом login")
    public void createDuplicateUserTests() {

        CreateCourier createCourier = generation.newCourier();
        Courier courierCredentials = new Courier(createCourier.getLogin(), createCourier.getPassword());
        step.createCourier(createCourier);

        step.createCourier(createCourier)
                .log().all()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierId = step.loginUserGetID(courierCredentials);

    }


    @Test
    @DisplayName("Создание Курьера - без поля password")
    @Description("Проверка, что возвращается ошибка, если при создании курьера не заполнен пароль")
    public void createUserWithoutPasswordTests() {
        CreateCourier createCourier = generation.newCourier();
        createCourier.setPassword(null);

        step.createCourier(createCourier)
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }

    @Test
    @DisplayName("Создание Курьера - без поля login")
    @Description("Проверка, что возвращается ошибка, если при создании курьера не заполнен login")
    public void createUserWithoutLoginTests() {
        CreateCourier createCourier = generation.newCourier();
        createCourier.setLogin(null);

        step.createCourier(createCourier)
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

    }
}
