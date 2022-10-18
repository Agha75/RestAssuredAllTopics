package com.practice.nisum.restassured.request.response.specification2;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestSpecBuilderClass {
    RequestSpecification requestSpecification;
    @BeforeClass
    public void beforeClass(){
         /*requestSpecification = with() // with() and given() both are same
                .baseUri("https://api.getpostman.com/")
                .header("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .log().all();*/


        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.getpostman.com/");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73");
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        //Chaining can be also done
       /* requestSpecBuilder
                .setBaseUri()
                .addHeader()*/
    }
    @Test
    public void requestSpecBuilder(){
        // requestSpecification which builds specBuilder object then it is passed given().spec()
        // if we want to pass extra header than that can be passed directly here also
        Response response = given().spec(requestSpecification)
                .header("dummy","dummyValue")
                .when()
                .get("workspaces")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    public void requestSpecification2(){
        // requestSpecification object is directly used with get()
        // requestSpecification object is directly passed in given()
        Response response = given(requestSpecification).get("workspaces").then().log().all().extract().response();
                assertThat(response.statusCode(), is(equalTo(200)));
                assertThat(response.path("workspaces[0].name").toString(),equalTo("My Workspace"));
    }
}
