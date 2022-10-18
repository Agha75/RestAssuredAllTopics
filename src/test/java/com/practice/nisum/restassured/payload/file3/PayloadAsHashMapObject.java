package com.practice.nisum.restassured.payload.file3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.matchesPattern;

public class PayloadAsHashMapObject {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setBaseUri("https://api.getpostman.com/")
                .addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        //Default Specification Used from Rest assured
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200);

        //Default Specification Used from Rest assured
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    String workSpaceId;
    String workSpaceName;
    @Test
    public void postCallPayloadAsHashMap_NonBDDStyle(){
        // Note default specification are used so they will not bbe called here in .spec()
        // with() will be used to avoid BDD and with() and given() are same just syntactical difference
        // Payload will bbe sent as Map object

        //HashMap with key and object which will have one key and object which is nested in payload
        HashMap<String, Object> hashMap= new HashMap<String, Object>();

        //HashMap for nested object this will have key value pairs
        HashMap<String, Object> nestedObject= new HashMap<String, Object>();
        nestedObject.put("name","TestingWorkSpaceAPI22HashMapObject");
        nestedObject.put("type","personal");
        nestedObject.put("description","This created y API call");

        //kay and object is passed in hashMap
        hashMap.put("workspace", nestedObject);


        Response response = with()
                /* body will not accept hashmap object directly jackson databind library
                will be installed which will convert hashmap object to json object and this is done
                 internally by Rest Assured with the help of Jackson library*/
                .body(hashMap)
                .post("workspaces");

        // This is asserting response without extracting
        assertThat(response.path("workspace.name"), is(equalTo("TestingWorkSpaceAPI22HashMapObject")));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));

        //And also can extract values by using this way
        workSpaceName=response.path("workspace.name");
        System.out.println(workSpaceName);
        workSpaceId=response.path("workspace.id");
        System.out.println(workSpaceId);

    }
}
