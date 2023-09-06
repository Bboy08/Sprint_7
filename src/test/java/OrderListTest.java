import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class OrderListTest {

    private ScooterServiceOrder order = new ScooterServiceOrder();

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
    public void listOrderExpectNotNullBody() {
        ValidatableResponse response = order.listOrder();
        assertNotNull(response.extract().body());
    }
}
