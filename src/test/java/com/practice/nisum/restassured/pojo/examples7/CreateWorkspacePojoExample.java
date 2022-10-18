package com.practice.nisum.restassured.pojo.examples7;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojos.complex.pojo.request.Workspace;
import pojos.complex.pojo.request.WorkspaceRoot;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateWorkspacePojoExample {
    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.getpostman.com/");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6320a87b50fa93297a8a5272-b4eef7324b66f33374bb0de69afffecd0f");
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
    public void createWorkspacePojoExampleTest(){
        Workspace workspace = new Workspace("pojoWorkSpace","personal","Testing Purpose created");
        WorkspaceRoot workspaceRoot= new WorkspaceRoot(workspace);

        WorkspaceRoot deserializedPojo=with()
                .body(workspaceRoot)
                .post("workspaces")
                .then()
                .extract()
                .response()
                .as(WorkspaceRoot.class);

        assertThat(deserializedPojo.getWorkspace().getName(), equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializedPojo.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
    }

    //This test is created to show that how one pojo can be used multiple times with different dataset
    @Test (dataProvider = "workspace")
    public void createWorkspacePojoExampleTestWithDataProvide(String name, String type, String description){
        Workspace workspace = new Workspace(name,type,description);
        WorkspaceRoot workspaceRoot= new WorkspaceRoot(workspace);

        WorkspaceRoot deserializedPojo=with()
                .body(workspaceRoot)
                .post("workspaces")
                .then()
                .extract()
                .response()
                .as(WorkspaceRoot.class);

        assertThat(deserializedPojo.getWorkspace().getName(), equalTo(workspaceRoot.getWorkspace().getName()));
        assertThat(deserializedPojo.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
    }
    //Data is made for above function and the function will be executed two times because there is two set of data
    @DataProvider(name = "workspace")
    public Object[][] getWorkspace(){
        return new Object[][]{
                {"dataWorkspace","personal","description"},
                {"dataWorkspace2","personal","description"}
        };
    }
}
