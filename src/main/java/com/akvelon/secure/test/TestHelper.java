package com.akvelon.secure.test;


public class TestHelper {
    public static String where (String param, String predicate, String value){
        return " "+predicate+ " "+"'"+value+"'";
    }
}
