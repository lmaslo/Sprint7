package Order.tests;

import Order.models.CreateOrder;
import Order.models.OrderGeneration;
import Order.steps.OrderSteps;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    OrderSteps orderSteps = new OrderSteps();
    OrderGeneration generation = new OrderGeneration();

    @Parameterized.Parameters(name = "color: {0}")
    public static Object[][] getColorForOrder() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}},
        };
    }

    @Parameterized.Parameter(0)
    public String[] color;

    @Test
    @DisplayName("Создание заказа")
    public void CreateOrderTests() {
        CreateOrder createOrder = generation.newOrder(color);
        orderSteps.CreateOrder(createOrder)
                .log().all()
                .statusCode(201)
                .body("track", notNullValue());
    }

}
