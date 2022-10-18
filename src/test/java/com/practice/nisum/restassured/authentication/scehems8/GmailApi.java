package com.practice.nisum.restassured.authentication.scehems8;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class GmailApi {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String access_token = "ya29.a0AVA9y1uEXjnl2flqJpPzGVIJ9zCq6jyaykeT6cG0CrztnF2Wy0vXOG8jMWlf9ci85fXrBic8o5VG5Zg4Hs8fX29j4X3yPX3uhz5zbSkV2lUyrJyCV2t3muBe6w73N2vSr6vsx-G_sKX3cY1tv6Pon2eNafPsaCgYKATASARMSFQE65dr8amv5ajJEw1NvicdezQ7c2A0163";
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://gmail.googleapis.com");
        requestSpecBuilder.addHeader("Authorization","Bearer "+access_token);
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectStatusCode(200);
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void getUserProfile(){
        given(requestSpecification)
                .basePath("gmail/v1")
                .pathParam("userid","syedroze.sr@gmail.com")
                .when()
                .get("users/{userid}/profile")
                .then().spec(responseSpecification);
    }

    @Test
    public void sendMessage(){
        String message = "From: syedroze.sr@gmail.com\n" +
                "To: agharoze.ar@gmail.com\n" +
                "Subject: Rest AssuredTest Email\n" +
                "\n" +
                "Sending from Rest Assured";

        String base64URLEncodedMessage = Base64.getUrlEncoder().encodeToString(message.getBytes());

        HashMap<String, String> hashMap=new HashMap<>();
        hashMap.put("raw",base64URLEncodedMessage);
        given(requestSpecification)
                .basePath("gmail/v1")
                .pathParam("email","syedroze.sr@gmail.com")
                .body(hashMap)
                .when()
                .post("users/{email}/messages/send")
                .then().spec(responseSpecification);
    }
}
