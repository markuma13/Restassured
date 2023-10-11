package com.example.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {
    protected final int nonexistentPetId = 132;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
    }

    @Test
    @DisplayName("Find pet by ID")
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
}
