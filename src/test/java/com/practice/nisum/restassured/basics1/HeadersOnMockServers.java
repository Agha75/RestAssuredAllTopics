package com.practice.nisum.restassured.basics1;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class HeadersOnMockServers {
    @Test
    public void multipleHeaders(){
        given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .header("header","value1")
                .header("x-mock-match-request-headers","header")
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    //This way is also correct for passing multiple headers

    Header getHeaderValue1 = new Header("header","value1");
    Header getHeaderValue2 = new Header("header","value2");
    Header matchHeader = new Header("x-mock-match-request-headers","header");
    @Test
    public void multipleHeadersWithObject(){
        given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .header(getHeaderValue2)
                .header(matchHeader)
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    // if there are many header instead of calling header again and again we will user Headers
    Headers headers = new Headers(getHeaderValue1,matchHeader);

    @Test
    public void multipleHeadersWithObjectHeaders(){
        given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .headers(headers)
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    // if there are many header instead of calling header again and again we will user Headers with Map also
    @Test
    public void multipleHeadersWithMap(){
        HashMap <String, String> hashMap= new HashMap<String, String>();
        hashMap.put("header","value1");
        hashMap.put("x-mock-match-request-headers","header");
        given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .headers(hashMap)
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200);
    }

    // if there header with multiValue
    @Test
    public void multiValueHeaderRequest(){
        Header header1 = new Header("multiValue", "value1");
        Header header2 = new Header("multiValue", "value1");
        Headers headers = new Headers(header1,header2);
        given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .headers(headers)

                // directly can also be send
                // .headers("multiValue","value1","value2")

                .log().headers()
                .when()
                .get("get")
                .then().log().all();
    }

    @Test
    public void assertResponseHeader(){
        HashMap <String, String> hashMap= new HashMap<String, String>();
        hashMap.put("header","value2");
        hashMap.put("x-mock-match-request-headers","header");
        given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .headers(hashMap)
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200)

                //Both ways are correct to validate multiple/single headers

                /*.header("responseHeader","resValue2")
                .header("X-RateLimit-Limit","120")*/

                .headers("responseHeader","resValue2",
                        "X-RateLimit-Limit","120");

    }

    @Test
    public void extractResponseHeader(){
        HashMap <String, String> hashMap= new HashMap<String, String>();
        hashMap.put("header","value2");
        hashMap.put("x-mock-match-request-headers","header");
        Headers extractedHeaders=given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .headers(hashMap)
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract()
                .headers();
        //for name we have to provide gatName
        System.out.println("***headerName***"+ extractedHeaders.get("responseHeader").getName());
        //for value both ways are correct
        System.out.println("***headerValue***"+ extractedHeaders.get("responseHeader").getValue());
        System.out.println("***headerValue***"+ extractedHeaders.getValue("responseHeader"));

        System.out.println("***************************************");


        //For of loop can be also used
        for (Header header: extractedHeaders){
            System.out.print("header name " + header.getName()+ " , ");
            System.out.println("header name " + header.getValue()+ " , ");
        }

    }

    @Test
    public void extractMultiValueResponseHeader(){
        HashMap <String, String> hashMap= new HashMap<String, String>();
        hashMap.put("header","value1");
        hashMap.put("x-mock-match-request-headers","header");
        Headers headers=given()
                .baseUri("https://ba74d2cd-fa55-4010-ae82-0796ca1c15c0.mock.pstmn.io/")
                .headers(hashMap)
                .when()
                .get("get")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().headers();

        List<String> value= headers.getValues("multiValueHeader");
        for (String values:value){
            System.out.println(values);
        }

    }
}
