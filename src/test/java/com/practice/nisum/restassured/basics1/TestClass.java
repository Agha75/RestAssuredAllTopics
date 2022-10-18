package com.practice.nisum.restassured.basics1;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.config.LogConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestClass {
    @Test
    public void getWorkspacePostman() {
        Response response=given()
                .log()
                .all()
                .baseUri("https://api.getpostman.com/")
                .header("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .when()
                .get("workspaces")
                .then()
                .log()
                .all()
                .assertThat().statusCode(200)
                .body("workspaces.name", hasItems("My Workspace", "TestingWorkSpace"),
                        "workspaces.type",hasItems("personal"),
                        "workspaces[0].name",is(equalTo("My Workspace")),
                        "workspaces.size",is(equalTo(2)),
                        "workspaces.name",hasItems("TestingWorkSpace"))
                .extract()
                .response();
        // Log response for better view
        System.out.println("Here is the response"+ response.asPrettyString());

        // Both ways are correct to extract single value
        String res=response.path("workspaces[0].name");
        System.out.println(res);
        // OR
        JsonPath jsonPath = new JsonPath(response.asString());
        String workspaceName = jsonPath.getString("workspaces[1].name");
        int workspaceSize =  jsonPath.getInt("workspaces.size");
        System.out.println(workspaceName);
        System.out.println(workspaceSize);

        //Hamcrest static assertion
        assertThat(workspaceName, is(equalTo("TestingWorkSpace")));

        // TestNG assertion
        Assert.assertEquals(workspaceSize,2);

    }

    @Test
    public void getWorkspaceWithHamcrestAssertion() {
        given()
                .baseUri("https://api.getpostman.com/")
                .header("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .when().get("workspaces")
                .then().log().all().assertThat().statusCode(200)
                .body("workspaces.name", contains("My Workspace", "TestingWorkSpace"),
                        "workspaces.name", containsInAnyOrder("TestingWorkSpace","My Workspace"),
                        "workspaces.name", is(not(empty())),
                        "workspaces.name", is(not(emptyArray())),
                        "workspaces.name", hasSize(2),
                        //"workspaces.name", endsWith("Workspace")
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry("name","My Workspace"),
                        "workspaces[0]", is(not(equalTo(Collections.EMPTY_MAP))),
                        "workspaces[0].name", allOf(startsWith("My"), containsString("Workspace"))
                        );
    }

    @Test
    public void getWorkspaceLogging() {
        Set<String> headers = new HashSet<>();
        headers.add("X-Api-Key");
        headers.add("Accept");

        given()
                .baseUri("https://api.getpostman.com/")
                .header("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                // The below line is used to log when validation fails in both request and response
                .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))

                // This will not log the header which helps hiding the sensitive information. this will hide single header
                // .config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key")))

                // this will blacklist multiple header and it accepts collection
                .config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers)))

                // .log().all()
                .when().get("workspaces")
                .then()
                // .log().all()
                // .ifValidationFails() // this will log when validation fails
                // .ifError() // this will print log if error occurs in response
                .assertThat().statusCode(200)
                .body("workspaces.name", contains("My Workspace", "TestingWorkSpace"));
    }
}
