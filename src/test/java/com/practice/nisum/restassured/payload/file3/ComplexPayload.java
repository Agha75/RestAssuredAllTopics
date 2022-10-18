package com.practice.nisum.restassured.payload.file3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.with;

public class ComplexPayload {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io")
                .setContentType("application/json;charset=utf-8")
                .addHeader("x-mock-match-request-body","true")
                .log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectContentType("application/json;charset=utf-8")
                .expectStatusCode(200)
                .log(LogDetail.ALL);
        RestAssured.responseSpecification=responseSpecBuilder.build();
    }


    @Test
    public void ComplexPayload_NonBDDStyle(){
        //List created for array of numbers in payload
        List<Integer> numberList= new ArrayList<Integer>();
        numberList.add(5);
        numberList.add(9);

        //HashMap created to accept String and Object
        HashMap<String, Object> batterHashMap2= new HashMap<>();
        batterHashMap2.put("id",numberList);
        batterHashMap2.put("type","Chocolate");

        HashMap<String,Object> batterHashMap= new HashMap<>();
        batterHashMap.put("id","1001");
        batterHashMap.put("type","Regular");

        List<HashMap<String,Object>> batterArrayList= new ArrayList<>();
        batterArrayList.add(batterHashMap);
        batterArrayList.add(batterHashMap2);

        HashMap<String,List> batter= new HashMap<>();
        batter.put("batter",batterArrayList);


        List<String> typeArrayList= new ArrayList<>();
        typeArrayList.add("Glazed");
        typeArrayList.add("Glazed2");

        HashMap<String, Object> toppingHashMap2= new HashMap<>();
        toppingHashMap2.put("id","5002");
        toppingHashMap2.put("type",typeArrayList);

        HashMap<String, Object> toppingHashMap= new HashMap<>();
        toppingHashMap.put("id","5001");
        toppingHashMap.put("type","None");

        List<HashMap<String, Object>> toppingArrayList= new ArrayList<>();
        toppingArrayList.add(toppingHashMap);
        toppingArrayList.add(toppingHashMap2);


        HashMap<String,Object> mainHashMap= new HashMap<>();
        mainHashMap.put("id","0001");
        mainHashMap.put("type","donut");
        mainHashMap.put("name","Cake");
        mainHashMap.put("ppu",0.55);
        mainHashMap.put("batters",batter);
        mainHashMap.put("topping",toppingArrayList);



        Response response = with()
                .body(mainHashMap)
                .post("/postComplexJson");

//        JsonPath jsonPath = new JsonPath(response.asString());
//        String nameHere = jsonPath.getString("");
    }
}
