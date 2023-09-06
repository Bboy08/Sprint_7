import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

public class CourierLoginTest {

    public static final Courier COURIER = new Courier("ninjafr987", "1234", "saske");
    public static final Courier COURIER1 = new Courier("", "1234", "saske");
    public static final Courier COURIER2 = new Courier("ninjafr987cerg", "1234", "saske");
    private ScooterServiceClient client = new ScooterServiceClient();

    @Before
    public void setUp() {
        RequestSpecification requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri(ScooterServiceClient.SCOOTER_SERVICE_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpec(requestSpec);
        client.createCourier(COURIER);
    }

    @Test
    public void loginUserExpect200() {
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER));
        response.assertThat().statusCode(SC_OK);
    }

    @Test
    public void loginUserWithoutLoginExpect400() {
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER1));
        response.assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void loginUserNonExistentExpect404() {
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER2));
        response.assertThat().statusCode(SC_NOT_FOUND);
    }

    @Test
    public void loginUserReturnId() {
        int id = client.login(Credentials.fromCourier(COURIER)).extract().body().jsonPath()
                .getInt("id");
        assert id != 0;
    }

    @After
    public void cleanUp() {
        int id = client.login(Credentials.fromCourier(COURIER)).extract().body().jsonPath()
                .getInt("id");
        client.deleteCourierById(id);
    }
}
