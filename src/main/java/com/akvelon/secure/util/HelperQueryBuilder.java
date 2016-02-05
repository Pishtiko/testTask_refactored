package com.akvelon.secure.util;


public class HelperQueryBuilder {
    public static String where (String param, String predicate, String value){
        return " "+predicate+ " "+"'"+value+"'";
    }
}
