package com.practice.nisum.restassured.authentication.scehems8;

import java.util.Base64;

public class Base64Encoding {
    public static void main(String[] args) {
        //Note Base64 takes String username:password and pass it into header
        String usernameColonPassword = "myUsername:myPassword";

        //Encoded
        String base64Encoded = Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
        System.out.println("Encoded "+base64Encoded);

        //Decoded
        byte[] decodedBytes = Base64.getDecoder().decode(base64Encoded);
        System.out.println("Decoded "+ new String(decodedBytes));

    }
}
