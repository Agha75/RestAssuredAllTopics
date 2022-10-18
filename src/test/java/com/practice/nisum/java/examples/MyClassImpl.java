package com.practice.nisum.java.examples;

public class MyClassImpl implements MyInterface{
    private MyInterface MyInterface;
    @Override
    public MyInterface printMe() {
        System.out.println("I'm me");
        return MyInterface;
    }
}
