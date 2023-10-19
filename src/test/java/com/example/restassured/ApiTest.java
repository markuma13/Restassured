package com.example.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {
    protected final int nonexistentPetId = 134;
    protected final int invalidPetId = 13;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Test
    @Order(1)
    @DisplayName("Find pet by error")
    public void petNotFoundError() {
        given().when()
                .get(baseURI + "pet/{id}", nonexistentPetId)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Pet not found"));
    }
    @Test
    @Order(2)
    @DisplayName("Added check pet")
    public void petAddedPost(){
        Integer id = 13;
        String name = "Kit";
        String status = "sold";

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "pet/")
                .then()
                .log().all()
                .time(lessThan(3000L))
                .statusCode(200)
                .body("id", equalTo(id))
                .body("name", equalTo(name))
                .body("status", equalTo(status));
    }

    @Test
    @Order(3)
    @DisplayName("Put check")
    public void checkPutPetPost(){
        Integer id = 13;
        String name = "Cat Samsa";
        String status = "on sale";

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        given().contentType("application/json")
                .body(request)
                .when()
                .put(baseURI + "pet/")
                .then()
                .log().all()
                .time(lessThan(3000L))
                .body("id", equalTo(id))
                .body("name", equalTo(name))
                .body("status", equalTo(status));
    }

    @Test
    @Order(4)
    @DisplayName("Find pet by ID")
    public void checkPetGet() {
        given().when()
                .log().all()
                .get(baseURI + "pet/{id}", invalidPetId)
                .then()
                .statusCode(200)
                .body("id", equalTo(13))
                .body("name", equalTo("Cat Samsa"))
                .body("status", equalTo("on sale"));
    }

}
