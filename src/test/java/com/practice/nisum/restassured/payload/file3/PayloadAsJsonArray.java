package com.practice.nisum.restassured.payload.file3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PayloadAsJsonArray {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setBaseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .addHeader("x-mock-match-request-body","true")

                //this will not check the default encoding format
                // .setConfig(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))

                // If we want to pass content type with encoding format the use this
                //.setContentType(ContentType.JSON)
                .setContentType("application/json;charset=utf-8")
                .log(LogDetail.ALL);
        //Default Specification Used from Rest assured
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200);

        //Default Specification Used from Rest assured
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void postCallPayloadAsJsonArray_NonBDDStyle(){
        // Note default specification are used so they will not bbe called here in .spec()
        // with() will be used to avoid BDD and with() and given() are same just syntactical difference
        // Payload will be sent as json array as list

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


        Response response = with()
                /* body will not accept ArrayList object directly jackson databind library
                will be installed which will convert ArrayList object to json object */
                .body(jsonList)
                .post("post");

        // This is asserting response without extracting
        assertThat(response.path("msg"), is(equalTo("Successs")));

        String message=response.path("msg");
        System.out.println(message);

    }
}
