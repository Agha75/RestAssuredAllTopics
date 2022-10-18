package com.practice.nisum.restassured.requestparamters4;

import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class MultiPartFormData {
    //Collection Used PostmanEcho
    @Test
    public void multipartFormDataParameter(){
        given()
                .log().all()
                .baseUri("https://postman-echo.com/")
                //This first way to pass path param
                .multiPart("foo1","bar1")
                .when()
                .post("post")
                .then()
                .log().all()
                .statusCode(200);

        //NOTE: for multiple multipart we can use .multipart multiple times
    }

    //Collection Used PostmanEcho
    @Test
    public void multipartUploadFileParameter(){
        String attributes = "{\"name\":\"UploadFile.txt\",\"parent\":{\"id\":\"123456\"}}";
        given()
                .log().all()
                .baseUri("https://postman-echo.com/")
                //This first way to pass path param
                .multiPart("file",new File("UploadFile.txt"))
                .multiPart("attributes", attributes, "application/json")
                .when()
                .post("post")
                .then()
                .log().all()
                .statusCode(200);

        //NOTE: for multiple multipart we can use .multipart multiple times
    }


}
