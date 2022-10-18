package com.practice.nisum.restassured.serialization.deserialization6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SerializationWithObjectMapper {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.getpostman.com/");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73");
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.addFilter(new RequestLoggingFilter(LogDetail.ALL));
        requestSpecBuilder.addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.expectStatusCode(200);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }
    //collection used PostmanWorkspace
    String workSpaceId;
    String workSpaceName;
    @Test
    public void serializationDoneWithObjectMapperExplicitly() throws JsonProcessingException {
        Map<String, Object> mainObject = new HashMap<>();

        Map<String, String> nestedObject = new HashMap<>();
        nestedObject.put("name","WorkSpaceWithExplicitSerialization");
        nestedObject.put("type","personal");
        nestedObject.put("description","This created y API call");
        mainObject.put("workspace", nestedObject);

        //This mapper is used to do serialization explicitly and this ObjectMapper from jackson
        //This is also done directly by rest Assured but it good to have knowledge that this can be done explicitly
        ObjectMapper objectMapper = new ObjectMapper();
        String JsonObjectResult = objectMapper.writeValueAsString(mainObject);

        Response response = with()
                .body(JsonObjectResult)
                .post("workspaces");

        // This is asserting response without extracting
        assertThat(response.path("workspace.name"), is(equalTo("WorkSpaceWithExplicitSerialization")));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));

        //And also can extract values by using this way
        workSpaceName=response.path("workspace.name");
        System.out.println(workSpaceName);
        workSpaceId=response.path("workspace.id");
        System.out.println(workSpaceId);
    }

    @Test
    public void serializationDoneWithObjectMapperAndObjectNode() throws JsonProcessingException {
        //This mapper is used to do serialization explicitly and this ObjectMapper from jackson
        //This is also done directly by rest Assured but it good to have knowledge that this can be done explicitly
        ObjectMapper objectMapper = new ObjectMapper();

        //This will create objectNode
        //ObjectNode is from Jackson and this can be also used to create Map/List
        ObjectNode nestedObjectNode = objectMapper.createObjectNode();
        nestedObjectNode.put("name","WorkSpaceWithObjectNode");
        nestedObjectNode.put("type","personal");
        nestedObjectNode.put("description","This created y API call");

        ObjectNode mainObjectNode= objectMapper.createObjectNode();
        mainObjectNode.set("workspace", nestedObjectNode);

        String JsonObjectNodeResult = objectMapper.writeValueAsString(mainObjectNode);

        Response response = with()
                // First Way
                // .body(JsonObjectNodeResult)

                // Direct Way
                .body(mainObjectNode)
                .post("workspaces");

        // This is asserting response without extracting
        assertThat(response.path("workspace.name"), is(equalTo("WorkSpaceWithObjectNode")));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));

        //And also can extract values by using this way
        workSpaceName=response.path("workspace.name");
        System.out.println(workSpaceName);
        workSpaceId=response.path("workspace.id");
        System.out.println(workSpaceId);
    }
}
