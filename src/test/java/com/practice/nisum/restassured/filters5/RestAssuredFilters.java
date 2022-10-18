package com.practice.nisum.restassured.filters5;

import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RestAssuredFilters {
    @Test
    public void loggingFilter(){
        given()
                .baseUri("https://postman-echo.com/")
                //filters are also used for logging
                //this will log both request and response
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter())
                .when()
                .get("get")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("EchoGetJsonSchema.json"));

    }

    @Test
    public void loggingSpecificFilter(){
        given()
                .baseUri("https://postman-echo.com/")
                //filters are also used for logging
                //this will log both request and response
                .filter(new RequestLoggingFilter(LogDetail.BODY))
                .filter(new ResponseLoggingFilter(LogDetail.STATUS))
                .when()
                .get("get")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("EchoGetJsonSchema.json"));

    }
}
