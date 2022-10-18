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
import pojos.create.postman.collection.CollectionRoot;
import pojos.create.postman.collectioncopy.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class CreateCollectionPojoExampleCopy {
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
        HeaderCopy headerCopy = new HeaderCopy("Content-Type", "application/json");
        List<HeaderCopy> headerList = new ArrayList<>();
        headerList.add(headerCopy);

        BodyCopy body = new BodyCopy("raw", "{\"data\":\"123\"}");

        RequestRequest request = new RequestRequest("https://getpostman-echo.com/post", "POST", headerList, body,
                "This is a simple Post Request");

        RequestRootRequest requestRoot = new RequestRootRequest("Sample POST Request", request);
        List<RequestRootRequest> requestRootList = new ArrayList<>();
        requestRootList.add(requestRoot);

        FolderRequest folder = new FolderRequest("Test folder", requestRootList);
        List<FolderRequest> folderList = new ArrayList<>();
        folderList.add(folder);

        //Generate random number
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;

        InfoCopy info = new InfoCopy("Test Collection " + randomInt, "Test Collection by rest assured", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        CollectionRequest collection = new CollectionRequest(info, folderList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);

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
        CollectionRootResponse collectionRootResponseDeserialize = given()
                .pathParam("collectionUid", collectionUID)
                .when()
                .get("collections/{collectionUid}")
                .then()
                .extract()
                .as(CollectionRootResponse.class);

        // This will serialize both object to jsonObjects
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deSerializedCollectionRootStr = objectMapper.writeValueAsString(collectionRootResponseDeserialize);

        //JsonAssert will accept jsonObject and helps to exclude which are not wanted and will do full body assertion
        //In below code it check the url to be present but it will not check its types
        //LENIENT -->this will not check the order of json
        //[*] it is used to list
        JSONAssert.assertEquals(collectionRootStr, deSerializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                        new Customization("collection.item[*].item[*].request.url", (o1, o2) -> true)));


        List<String> UrlRequestList = new ArrayList<>();
        List<String> UrlResponseList = new ArrayList<>();

        for (RequestRootRequest requestRootRequest : requestRootList) {
            System.out.println("Url from request payload " + requestRootRequest.getRequest().getUrl());
            UrlRequestList.add(requestRootRequest.getRequest().getUrl());
        }

        List<FolderResponse> folderResponseList = collectionRootResponseDeserialize.getCollection().getItem();
        for (FolderResponse folderResponse : folderResponseList) {
            List<RequestRootResponse> requestRootResponsesList = folderResponse.getItem();
            for (RequestRootResponse requestRootResponse : requestRootResponsesList) {
                URL url = requestRootResponse.getRequest().getUrl();
                System.out.println("Url from response " + url.getRaw());
                UrlResponseList.add(url.getRaw());
            }
        }


        assertThat(UrlResponseList, containsInAnyOrder(UrlRequestList.toArray()));
    }

    @Test
    public void createEmptyCollectionComplexPojo() throws JsonProcessingException, JSONException {
        List<FolderRequest> folderList = new ArrayList<>();

        InfoCopy info = new InfoCopy("Test Collection 2", "Test Collection by rest assured", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        CollectionRequest collection = new CollectionRequest(info, folderList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);

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
        CollectionRootResponse collectionRootResponseDeserialize = given()
                .pathParam("collectionUid", collectionUID)
                .when()
                .get("collections/{collectionUid}")
                .then()
                .extract()
                .as(CollectionRootResponse.class);

        // This will serialize both object to jsonObjects
        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String deSerializedCollectionRootStr = objectMapper.writeValueAsString(collectionRootResponseDeserialize);

        //JsonAssert will accept jsonObject and helps to exclude which are not wanted and will do full body assertion
        //In below code it check the url to be present but it will not check its types
        //LENIENT -->this will not check the order of json
        //[*] it is used to list
        /*JSONAssert.assertEquals(collectionRootStr, deSerializedCollectionRootStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                        new Customization("collection.item[*].item[*].request.url", (o1, o2) -> true)));*/


        assertThat(objectMapper.readTree(collectionRootStr),
                equalTo(objectMapper.readTree(deSerializedCollectionRootStr)));

    }
}
