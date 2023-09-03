import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
    private ScooterServiceClient client = new ScooterServiceClient();
    private final Order ORDER;

    public OrderCreateTest(Order ORDER) {
        this.ORDER = ORDER;
    }

    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][] {
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"})},
                {new Order("Naruto", "Uchiha", "Konoha, 142 apt.", 4, "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{})}
        };
    }



    @Before
    public void setUp() {
        RequestSpecification requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri(SCOOTER_SERVICE_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpec(requestSpec);
    }

    @Test
    public void createOrder_expect201_and_notNull() {
        ValidatableResponse response = client.createOrder(ORDER);
        assertEquals(response.extract().statusCode(), 201);
        response.assertThat().body("track", notNullValue());
    }


}
