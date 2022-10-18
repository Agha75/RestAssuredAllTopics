package com.practice.nisum.restassured.request.response.specification2;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationClass {
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.getpostman.com/");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73");
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        responseSpecification = RestAssured.expect().statusCode(200).contentType(ContentType.JSON);
    }

    @Test
    public void responseSpecification() {
        //Response specification is directly used in spec after then()
        get("workspaces")
                .then()
                .spec(responseSpecification);
    }

    @Test
    public void responseSpecification2() {
        Response response = get("workspaces").then().spec(responseSpecification).log().all().extract().response();
        assertThat(response.path("workspaces[0].name").toString(), equalTo("My Workspace"));
    }
}
