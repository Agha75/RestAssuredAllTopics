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
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.create.postman.collection.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreateCollectionPojoExample {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.getpostman.com/");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73");
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.addFilter(new RequestLoggingFilter(LogDetail.ALL));
        requestSpecBuilder.addFilter(new ResponseLoggingFilter(LogDetail.ALL));
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecBuilder.expectStatusCode(200);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void createCollectionComplexPojo() throws JsonProcessingException, JSONException {
        Header header = new Header("Content-Type", "application/json");
        List<Header> headerList = new ArrayList<>();
        headerList.add(header);

        Body body = new Body("raw", "{\"data\":\"123\"}");

            Request request = new Request("https://getpostman-echo.com/post", "POST", headerList, body, "This is a simple Post Request");

        RequestRoot requestRoot = new RequestRoot("Sample POST Request", request);
        List<RequestRoot> requestRootList = new ArrayList<>();
        requestRootList.add(requestRoot);

        Folder folder = new Folder("Test folder", requestRootList);
        List<Folder> folderList = new ArrayList<>();
        folderList.add(folder);

        //Generate current date and time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String str = dtf.format(now);

        Info info = new Info("Test Collection " + str, "Test Collection by rest assured", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        Collection collection = new Collection(info, folderList);

        CollectionRoot collectionRoot = new CollectionRoot(collection);

        Response response = given()
                .body(collectionRoot)
                .when()
                .post("collections")
                .then()
                .extract()
                .response();

        JsonPath jsonPath = new JsonPath(response.asString());
        String collectionUID = jsonPath.getString("collection.uid");
        System.out.println(collectionUID);

        // Now creating GET call to check the response of create collection above
        CollectionRoot collectionRootDeserialize = given()
                .pathParam("collectionUid", collectionUID)
                .when()
                .get("collections/{collectionUid}")
                .then()
                .extract()
                .as(CollectionRoot.class);

        // This will serialize both object to jsonObjects
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deSerializedCollectionRootStr = objectMapper.writeValueAsString(collectionRootDeserialize);

        //JsonAssert will accept jsonObject and helps to exclude which are not wanted and will do full body assertion
        //In below code it check the url to be present but it will not check its types
        //LENIENT -->this will not check the order of json
        //[*] it is used to list
        JSONAssert.assertEquals(collectionRootStr, deSerializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
                    public boolean equal(Object o1, Object o2){
                        return true;
                    }
                })));
    }
}
