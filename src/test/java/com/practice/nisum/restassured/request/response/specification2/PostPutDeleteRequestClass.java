package com.practice.nisum.restassured.request.response.specification2;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostPutDeleteRequestClass {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setBaseUri("https://api.getpostman.com/")
                .addHeader("X-Api-Key", "PMAK-6246e88e61cf9759a846ca95-bcb8102504499c7d1335a2ec8d07cf7c73")
                .setContentType(ContentType.JSON)
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
    public void postCallToCreateWorkSpace_BDDStyle(){
        // Note default specification are used so they will not bbe called here in .spec()
        //.body can accept the payload in many ways
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"WorkSpacePostBDDStyle\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This created y API call\"\n" +
                "    }\n" +
                "}";
        Response response=given()
                .body(payload)
                .when()
                .post("workspaces")
                .then()
                // With default specification the logging does not work for response we have to explicitly mentioned this here
                .log().all()
                .assertThat()
                .body("workspace.name", is(equalTo("WorkSpacePostBDDStyle")),
                "workspace.id", matchesPattern("^[a-z0-9-]{36}$"))
                .extract().response();

        // This way is used to extract response
        JsonPath jsonPath = new JsonPath(response.asString());
        String workSpaceName = jsonPath.getString("workspace.name");
        String id =jsonPath.getString("workspace.id");
        System.out.println(workSpaceName);
        System.out.println(id);
    }

    //Chaining of Post / Put / Delete

    String workSpaceId;
    String workSpaceName;
    @Test
    public void postCallToCreateWorkSpace_NonBDDStyle(){
        //Note default specification are used so they will not bbe called here in .spec()
        // with() will be used to avoid BDD and with() and given() are same just syntactical difference
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"WorkSpacePostNonBDDStyle\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This created y API call\"\n" +
                "    }\n" +
                "}";
        Response response = with()
                .body(payload)
                .post("workspaces");

        // This is asserting response without extracting
        assertThat(response.path("workspace.name"), is(equalTo("WorkSpacePostNonBDDStyle")));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));

        //And also can extract values by using this way
        workSpaceName=response.path("workspace.name");
        System.out.println(workSpaceName);
        workSpaceId=response.path("workspace.id");
        System.out.println(workSpaceId);

    }

    @Test  (dependsOnMethods = {"postCallToCreateWorkSpace_NonBDDStyle"})
    public void putCallToCreateWorkSpace_NonBDDStyle(){
        //Note default specification are used so they will not bbe called here in .spec()
        // with() will be used to avoid BDD and with() and given() are same just syntactical difference
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"WorkSpacePostNonBDDStyleUpdateRestAssured\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"This created y API call\"\n" +
                "    }\n" +
                "}";
        Response response = with()
                .body(payload)
                //Path param is set which will get the id
                .pathParam("workspaceID", workSpaceId)
                .put("workspaces/{workspaceID}");

        // This is asserting response without extracting
        assertThat(response.path("workspace.name"), is(equalTo("WorkSpacePostNonBDDStyleUpdateRestAssured")));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));

        //And also can extract values by using this way
        workSpaceName=response.path("workspace.name");
        System.out.println(workSpaceName);
        workSpaceId=response.path("workspace.id");
        System.out.println(workSpaceId);
    }

    @Test  (dependsOnMethods = {"postCallToCreateWorkSpace_NonBDDStyle"})
    public void deleteCallToCreateWorkSpace_NonBDDStyle(){
        //Note default specification are used so they will not bbe called here in .spec()
        // with() will be used to avoid BDD and with() and given() are same just syntactical difference
        Response response = with()
                //Path param is set which will get the id
                .pathParam("deleteWorkspaceID",workSpaceId)
                // This will use the key which is storing the value of workspaceId and append it
                .delete("workspaces/{deleteWorkspaceID}");

        // This is asserting response without extracting
        assertThat(response.path("workspace.id"), is(equalTo(workSpaceId)));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));

        //And also can extract values by using this way
        workSpaceId=response.path("workspace.id");
        System.out.println(workSpaceId);
    }
}
