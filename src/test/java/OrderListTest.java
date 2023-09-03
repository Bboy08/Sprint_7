import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {
    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
    private ScooterServiceClient client = new ScooterServiceClient();

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
    public void listOrder_expect_notNullBody() {
        ValidatableResponse response = client.listOrder();
        assertNotNull(response.extract().body());
    }
}
