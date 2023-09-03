import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {

    public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
    public static final Courier COURIER = new Courier("ninjafr987", "1234", "saske");
    public static final Courier COURIER1 = new Courier("", "1234", "saske");
    public static final Courier COURIER2 = new Courier("ninjafr987cerg", "1234", "saske");
    private ScooterServiceClient client = new ScooterServiceClient();

    @Before
    public void setUp() {
        RequestSpecification requestSpec =
                new RequestSpecBuilder()
                        .setBaseUri(SCOOTER_SERVICE_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpec(requestSpec);
        client.createCourier(COURIER);
    }

    @Test
    public void loginUser_expect200() {
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER));
        assertEquals(response.extract().statusCode(), 200);
    }

    @Test
    public void loginUser_withoutLogin_expect400() {
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER1));
        assertEquals(response.extract().statusCode(), 400);
    }

    @Test
    public void loginUser_non_existent_expect404() {
        ValidatableResponse response = client.login(Credentials.fromCourier(COURIER2));
        assertEquals(response.extract().statusCode(), 404);
    }

    @Test
    public void loginUser_return_id() {
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
