import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ScooterServiceOrder {
    public static final String CREATE_ORDER_ENDPOINT = "/api/v1/orders";
    private RequestSpecification requestSpec;

    public void setRequestSpec(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
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
