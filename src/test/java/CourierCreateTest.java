import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

public class CourierCreateTest {

  public static final String SCOOTER_SERVICE_URI = "https://qa-scooter.praktikum-services.ru/";
  public static final Courier COURIER = new Courier("ninjafr987", "1234", "saske");
  public static final Courier COURIER_WITHOUT_LOGIN = new Courier("", "1234", "saske");
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
  @DisplayName("Create User - expect201")
  public void createUser_expect201() {
    ValidatableResponse response = client.createCourier(COURIER);
    assertEquals(response.extract().statusCode(), 201);
  }

  @Test
  @DisplayName("Create User - ok")
  public void createUser_expectOk() {
    ValidatableResponse response = client.createCourier(COURIER);
    assertTrue(response.extract().body().jsonPath().getBoolean("ok"));
  }

  @Test
  @DisplayName("Create User without login")
  public void createUser_withoutLogin_expect400() {
    ValidatableResponse response = client.createCourier(COURIER_WITHOUT_LOGIN);
    assertEquals(response.extract().statusCode(), 400);
    client.createCourier(COURIER);
  }

  @Test
  @DisplayName("Create 2 Users")
  public void create2Users_expect409() {
    client.createCourier(COURIER);
    ValidatableResponse response = client.createCourier(COURIER);
    assertEquals(response.extract().statusCode(), 409);
  }



  @After
  public void cleanUp() {
    int id = client.login(Credentials.fromCourier(COURIER)).extract().body().jsonPath()
        .getInt("id");
    client.deleteCourierById(id);
  }
}
