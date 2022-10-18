package com.practice.nisum.restassured.pojo.examples7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.simple.pojo.request.SimplePojoClass;

import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimplePojoExample {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/");
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.addFilter(new RequestLoggingFilter(LogDetail.ALL));
        requestSpecBuilder.addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectContentType(ContentType.JSON);

    }


    //Collection Used MockServer(Request= simplePojoRequest)
    @Test
    public void simplePojoRequest() throws JsonProcessingException {
       // Calling the POJO Class object
        SimplePojoClass simplePojoClass = new SimplePojoClass("value1", "value2");

        SimplePojoClass deserializeData = with()
                .body(simplePojoClass)
                .post("simplePojo")
                .then()

//                 Some assertions
//                .assertThat().body("key1",equalTo(simplePojoClass.getKey1()),
//                        "key2", equalTo(simplePojoClass.getKey2()));


                //now doing deserialization
                .extract()
                .response()
                .as(SimplePojoClass.class);

        // Now serializing both objects
        String keyOne=deserializeData.getKey1();
        System.out.println(keyOne);
        ObjectMapper objectMapper = new ObjectMapper();
        String deserializePojoStr =objectMapper.writeValueAsString(deserializeData);
//        String keyOne=deserializeData.getKey1();
//        System.out.println(keyOne);
        String simplePojoStr =objectMapper.writeValueAsString(simplePojoClass);

        //Now comparing both because both are same
        assertThat(objectMapper.readTree(deserializePojoStr), equalTo(objectMapper.readTree(simplePojoStr)));


    }
}
