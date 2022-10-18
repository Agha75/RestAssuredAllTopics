package com.practice.nisum.restassured.basics1;

import org.testng.annotations.Test;

public class Annotations {
    @Test
    public void shariq(){
        System.out.println("1. Shariq");
    }

    @Test(dependsOnMethods = {"imran"})
    public void agha(){
        System.out.println("3. agha");
    }

    @Test(dependsOnMethods = {"shariq"})
    public void imran(){
        System.out.println("2. imran");
    }
}
