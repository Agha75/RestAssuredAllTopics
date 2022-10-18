package com.practice.nisum.restassured.filters5;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ReUseFiltersForMultipleTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        //The log file will be created
        PrintStream printStream= new PrintStream("restAssured.log");

        //This will log both request and response in the file
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        //Both Request/Response Filters accepts PrintStream Object
        requestSpecBuilder.addFilter(new RequestLoggingFilter(printStream));
        requestSpecBuilder.addFilter(new ResponseLoggingFilter(printStream));
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder .build();

    }

    @Test
    public void loggingFilter(){
        given().spec(requestSpecification)
                .baseUri("https://postman-echo.com/")
                //If extra filter is required we can directly add here for validation purpose
                // .filter(new RequestLoggingFilter(LogDetail.BODY))
                .when()
                .get("get")
                .then().spec(responseSpecification)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("EchoGetJsonSchema.json"));

    }
}
