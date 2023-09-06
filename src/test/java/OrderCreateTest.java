import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.notNullValue;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    private ScooterServiceOrder order = new ScooterServiceOrder();
    private final Order ORDER;

    public OrderCreateTest(Order ORDER) {
        this.ORDER = ORDER;
    }

    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][] {
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"GREY"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{})}
        };
    }



    @Before
    public void setUp() {
        RequestSpecification requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri(ScooterServiceClient.SCOOTER_SERVICE_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        order.setRequestSpec(requestSpec);
    }

    @Test
    public void createOrderExpect201AndNotNull() {
        ValidatableResponse response = order.createOrder(ORDER);
        response.assertThat().statusCode(SC_CREATED);
        response.assertThat().body("track", notNullValue());
    }


}
