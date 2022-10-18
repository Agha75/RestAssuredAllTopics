package com.practice.nisum.restassured.authentication.scehems8;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CookieExample {
    SessionFilter sessionFilter = new SessionFilter();
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        //The below line is used when https is not supported
        requestSpecBuilder.setRelaxedHTTPSValidation();
        requestSpecBuilder.setBaseUri("https://localhost:8443/");
        RestAssured.requestSpecification = requestSpecBuilder.build();

    }

    //spring boot application (RomanianCodeExample) used for csrf token
    @Test
    public void formAuthenticationUsingCSRF_cookieExample(){

        Response response=given()
                .auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername",
                        "txtPassword").withAutoDetectionOfCsrf()) //withAutoDetectionOfCsrf() will fetch csrf automatically from response
                //this is used to get the session id
                .filter(sessionFilter)
                .log().all()
                .when()
                .get("login")
                .then()
                .log().all()
                .assertThat().statusCode(200).extract().response();

        System.out.println("**********  "+sessionFilter.getSessionId());


        given()
                //now the cookie send in cookie() function with Key-Value pair
                .cookie("JSESSIONID",sessionFilter.getSessionId())
                .log().all()
                .when()
                .get("profile/index")
                .then()
                .log().all()
                .assertThat().statusCode(200).body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void formAuthenticationUsingCSRF_multipleCookieExample(){

        Response response=given()
                .auth().form("dan", "dan123", new FormAuthConfig("/signin", "txtUsername",
                        "txtPassword").withAutoDetectionOfCsrf()) //withAutoDetectionOfCsrf() will fetch csrf automatically from response
                //this is used to get the session id
                .filter(sessionFilter)
                .log().all()
                .when()
                .get("login")
                .then()
                .log().all()
                .assertThat().statusCode(200).extract().response();

        System.out.println("**********  "+sessionFilter.getSessionId());


        //Sending multiple cookies with Cookie Builder
        Cookie cookie= new Cookie.Builder("JSESSIONID", sessionFilter.getSessionId()).setSecured(true)
                .setHttpOnly(true).setComment("my cookie").build();
        Cookie cookie2= new Cookie.Builder("dummy", "dummyValue").build();

        Cookies cookies= new Cookies(cookie,cookie2);


        given()
                //now the cookie send in cookie() function with Key-Value pair
                .cookies(cookies)
                .log().all()
                .when()
                .get("profile/index")
                .then()
                .log().all()
                .assertThat().statusCode(200).body("html.body.div.p", equalTo("This is User Profile\\Index. Only authenticated people can see this"));
    }

    @Test
    public void fetchSingleCookieExample(){
        Response response=given()
                .log().all()
                .when()
                .get("profile/index")
                .then()
                .log().all().extract().response();

        //this will return the value of cookie from response header
        System.out.println(response.getCookie("JSESSIONID"));

        //this will return the key-value of cookie from response header
        System.out.println(response.getDetailedCookie("JSESSIONID"));

    }

    @Test
    public void fetchMultipleCookiesExample(){
        Response response=given()
                .log().all()
                .when()
                .get("profile/index")
                .then()
                .log().all().extract().response();

        //get multiple cookies and this will return the object
        Map<String, String> cookies=response.getCookies();
        System.out.println(cookies);

        //Or iterate
        for (Map.Entry<String, String> entry: cookies.entrySet()){
            String key=entry.getKey();
            System.out.println("key = "+key);
            //or direct
            System.out.println("value = "+entry.getValue());
        }

        //get list of all detailed cookies
        Cookies cookies1=response.getDetailedCookies();
        //convert all cookies to list so they can be fetched easily
        List<Cookie> cookieList=cookies1.asList();
        for (Cookie cookie:cookieList){
            System.out.println("cookie ="+cookie.toString());
        }
    }
}
