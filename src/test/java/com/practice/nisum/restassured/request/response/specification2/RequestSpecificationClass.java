package com.practice.nisum.restassured.request.response.specification2;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RequestSpecificationClass {
    RequestSpecification requestSpecification;
    @BeforeClass
    public void beforeClass(){
         requestSpecification = with() // with() and given() both are same
                .baseUri("https://api.getpostman.com/")
                .header("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .log().all();
    }
    @Test
    public void requestSpecification(){
        //// requestSpecification object is passed in given() / given().spec()
        given().spec(requestSpecification)
                .when()
                .get("workspaces")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void requestSpecification2(){
        // requestSpecification object is directly used with get()
        Response response = requestSpecification.get("workspaces").then().log().all().extract().response();
                assertThat(response.statusCode(), is(equalTo(200)));
                assertThat(response.path("workspaces[0].name").toString(),equalTo("My Workspace"));
    }
}
