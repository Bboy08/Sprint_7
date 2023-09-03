import static io.restassured.RestAssured.given;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class ScooterServiceClient {

  public static final String CREATE_COURIER_ENDPOINT = "/api/v1/courier";
  public static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";
  public static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";
  private RequestSpecification requestSpec;

  public void setRequestSpec(RequestSpecification requestSpec) {
    this.requestSpec = requestSpec;
  }

  public ValidatableResponse createCourier(Courier courier) {
    return given()
        .spec(requestSpec)
        .log()
        .all()
        .body(courier)
        .post(CREATE_COURIER_ENDPOINT)
        .then()
        .log()
        .all();
  }

  public ValidatableResponse login(Credentials credentials) {
    return given()
        .spec(requestSpec)
        .log()
        .all()
        .body(credentials)
        .post(COURIER_LOGIN_ENDPOINT)
        .then()
        .log()
        .all();
  }

  public ValidatableResponse deleteCourierById(int id) {
    return given()
            .spec(requestSpec)
            .log()
            .all()
            .delete(CREATE_COURIER_ENDPOINT + "/" + id)
            .then()
            .log()
            .all();
  }

  public ValidatableResponse createOrder(Order order) {
    return given()
            .spec(requestSpec)
            .log()
            .all()
            .body(order)
            .post(CREATE_ORDER_ENDPOINT)
            .then()
            .log()
            .all();
  }

  public ValidatableResponse listOrder() {
    return given()
            .spec(requestSpec)
            .log()
            .all()
            .get(CREATE_ORDER_ENDPOINT)
            .then()
            .log()
            .all();
  }
}
