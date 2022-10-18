package com.practice.nisum.restassured.requestparamters4;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class RequestParameters {
    //Collection Used PostmanEcho
    @Test
    public void queryParameter(){
        given()
                .baseUri("https://postman-echo.com/")
                //Second way to send param
                .queryParam("foo1","bar1")
                // First way to send param
                // .param("foo1","bar1")
                .when()
                .get("get")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void multipleQueryParameter(){
        Map<String, String> map= new HashMap<>();
        map.put("foo1","bar1");
        map.put("foo2","bar2");

        given()
                .baseUri("https://postman-echo.com/")
                //Second way to send multiple params . Create map and pass object here in queryParams()
                .queryParams(map)

                // First way to send multiple param
                // .queryParam("foo1","bar1")
                // .queryParam("foo2","bar2")
                .when()
                .get("get")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void multipleValueQueryParameter(){
        given()
                .baseUri("https://postman-echo.com/")
                .queryParam("foo1","bar1,bar2,bar3")
                .when()
                .get("get")
                .then()
                .log().all()
                .statusCode(200);
    }

    //Collection Used restAssuredUdemy
    @Test
    public void pathParameter(){
        given()
                .log().all()
                .baseUri("https://jsonplaceholder.typicode.com/")
                //This first way to pass path param
                .pathParam("userId","1")
                .when()
                //here is the userId String is passed to append the path param
                .get("posts/{userId}")
                .then()
                .log().all()
                .statusCode(200);


        //NOTE: for multiple path param we user .pathParams() and inside we pass the map object just like in above examples
    }

}
