package com.practice.nisum.restassured.requestparamters4;

import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class FormUrlEncoding {
    @Test
    public void formUrlEncodingTest(){
        given()
                .log().all()
                .baseUri("https://postman-echo.com/")
                // This is used not to set default content type
                //This is done because it sends form_url _Encoding format and making it false then will not check the default content type
                .config(config.encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                // First way to send data in form param and these are used in formUrlEncoding
                .formParam("foo1", "bar1")
                .formParam("foo 2", "bar2")
                .when()
                .post("post")
                .then()
                .log().all()
                .statusCode(200);

    }
}
