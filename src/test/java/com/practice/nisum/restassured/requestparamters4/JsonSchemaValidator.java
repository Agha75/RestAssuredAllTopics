package com.practice.nisum.restassured.requestparamters4;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class JsonSchemaValidator {
    @Test
    public void JsonSchema(){
        given()
                .log().all()
                .baseUri("https://postman-echo.com/")
                .when()
                .get("get")
                .then()
                .log().all()
                .statusCode(200)
                // Schema is used for mandatory fields and validating the dataTypes
                // static method is imported for validating json schema kept under resources folder with name "EchoGetJsonSchema"
                .body(matchesJsonSchemaInClasspath("EchoGetJsonSchema.json"));

    }
}
