package com.practice.nisum.java.examples;

public class MethodChaining {
    public static void main(String[] args) {
        MethodChaining.a().b().c();
    }
    public static MethodChaining a(){
        System.out.println("a methods");
        return new MethodChaining();
    }
    public static MethodChaining b(){
        System.out.println("b methods");
        return new MethodChaining();
    }
    public static MethodChaining c(){
        System.out.println("c methods");
        return new MethodChaining();
    }
}
