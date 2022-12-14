package order.tests;

import order.steps.OrderSteps;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class GetListOrdersTests {

    OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Запрос список заказов")
    @Description("Проверка кода ответа и что в ответе возвращается список заказов")
    public void getOrderListTests() {

        orderSteps.getOrderList()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}
