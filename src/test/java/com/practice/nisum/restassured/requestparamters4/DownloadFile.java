package com.practice.nisum.restassured.requestparamters4;

import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;

public class DownloadFile {
    @Test
    public void downloadFile() throws IOException {

       byte[] bytes= given()
                .log().all()
                .baseUri("https://raw.githubusercontent.com/")
                .when()
                .post("appium/appium/master/sample-code/apps/ApiDemos-debug.apk")
                .then()
                .log().all()
                .extract().asByteArray();

        //Download File name will be this
        OutputStream outputStream = new FileOutputStream("ApiDemos-debug.apk");
        outputStream.write(bytes);
        outputStream.close();
    }

    //same process will input stream
    @Test
    public void downloadFileWithInputStream() throws IOException {

        InputStream inputStream= given()
                .log().all()
                .baseUri("https://raw.githubusercontent.com/")
                .when()
                //This will download File on root directory
                .post("appium/appium/master/sample-code/apps/ApiDemos-debug.apk")
                .then()
                .log().all()
                .extract().asInputStream();

        //Download File name will be this
        OutputStream outputStream = new FileOutputStream("ApiDemos-debug2.apk");
        //Input stream is read by bytes and then bytes object is passed to .write() and .close()
        byte[] bytes= new byte[inputStream.available()];
        outputStream.write(bytes);
        outputStream.close();
    }
}
