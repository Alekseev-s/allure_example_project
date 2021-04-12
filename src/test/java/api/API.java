package api;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class API {
    public Map<String, String> login(String email, String password) {
        return given()
                .formParam("Email", email)
                .formParam("Password", password)
                .when()
                .post("/login")
                .then()
                .log().ifError()
                .statusCode(302)
                .extract().cookies();
    }
}
