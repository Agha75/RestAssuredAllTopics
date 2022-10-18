package com.practice.nisum.restassured.request.response.specification2;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DefaultRequestSpecification {

    @BeforeClass
    public void beforeClass(){
        //for this default request specification can e also used

         /*requestSpecification = with() // with() and given() both are same
                .baseUri("https://api.getpostman.com/")
                .header("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .log().all();*/


        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.getpostman.com/");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73");
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();
        // This is default requestSpecification Class called from rest assured
    }
    @Test
    public void defaultRequestSpecification(){
        // here static import is used for get()
        // request default specification is from rest assured
        Response response = get("workspaces")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void defaultRequestSpecification2(){
        // here static import is used for get()
        // request default specification is from rest assured
        Response response = get("workspaces").then().log().all().extract().response();
                assertThat(response.statusCode(), is(equalTo(200)));
                assertThat(response.path("workspaces[0].name").toString(),equalTo("My Workspace"));
    }

    @Test
    public void queryRequestSpecification2(){
        // Query Request Specification is used to fetch the information send in the request
        // This will work will both normal specification or default specification
        QueryableRequestSpecification queryableRequestSpecification =
                SpecificationQuerier.query(RestAssured.requestSpecification);
        System.out.println("Base Uri =    "+queryableRequestSpecification.getBaseUri());
        System.out.println("Header   =    "+queryableRequestSpecification.getHeaders());
    }
}
