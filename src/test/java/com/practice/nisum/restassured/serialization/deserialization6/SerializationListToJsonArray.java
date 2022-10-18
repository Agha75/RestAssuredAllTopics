package com.practice.nisum.restassured.serialization.deserialization6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SerializationListToJsonArray {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/");
        requestSpecBuilder.addHeader("x-mock-match-request-body","true");
        requestSpecBuilder.setContentType("application/json;charset=utf-8");
        requestSpecBuilder.addFilter(new RequestLoggingFilter(LogDetail.ALL));
        requestSpecBuilder.addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.expectStatusCode(200);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void serializationForJsonArrayDoneWithObjectMapper() throws JsonProcessingException {
        //First HashMap
        HashMap<String, String> hashMap= new HashMap<String, String>();
        hashMap.put("id","5001");
        hashMap.put("type","None");
        //Second HashMap
        HashMap<String, String> hashMap1= new HashMap<String, String>();
        hashMap1.put("id","5002");
        hashMap1.put("type","Glazed");

        //List will be created with type HashMap and then that will be converted by jackson databind into json object
        List<HashMap<String, String>> jsonList= new ArrayList<HashMap<String,String>>();
        jsonList.add(hashMap);
        jsonList.add(hashMap1);

        ObjectMapper objectMapper= new ObjectMapper();
        String jsonListResult = objectMapper.writeValueAsString(jsonList);

        Response response = with()
                .body(jsonListResult)
                .post("post");

        // This is asserting response without extracting
        assertThat(response.path("msg"), is(equalTo("Successs")));

        String message=response.path("msg");
        System.out.println(message);

    }

    @Test
    public void serializationForJsonArrayDoneWithArrayNode() throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();

        ObjectNode objectNodeOne= objectMapper.createObjectNode();
        objectNodeOne.put("id","5001");
        objectNodeOne.put("type","None");

        ObjectNode objectNodeTwo= objectMapper.createObjectNode();
        objectNodeTwo.put("id","5002");
        objectNodeTwo.put("type","Glazed");

        ArrayNode arrayNode = objectMapper.createArrayNode();
        arrayNode.add(objectNodeOne);
        arrayNode.add(objectNodeTwo);
        String jsonListResult = objectMapper.writeValueAsString(arrayNode);

        Response response = with()
                //ArrayNode object cannot be send directly because body only accepts String/Object/File
                .body(jsonListResult)
                .post("post");

        // This is asserting response without extracting
        assertThat(response.path("msg"), is(equalTo("Successs")));

        String message=response.path("msg");
        System.out.println(message);

    }
}
