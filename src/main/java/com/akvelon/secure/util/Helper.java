package com.akvelon.secure.util;

import com.akvelon.secure.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Helper {

    public static String getCurrentUsersName() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
//            String role = auth.getCredentials().toString();
            String role1 = auth.getAuthorities().toString();
            System.out.println(role1);
            System.out.println("User is " + username);                          //debug notes
            return username;
        } catch (Exception e) {
            System.out.println(e + " in Helper method"+"/n"+e.getStackTrace());
            return "Error";
        }

    }

    public static User getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User username = new User();
            username.setLogin(auth.getName());
            username.setPassword("asd");
            //
            return username;
        } catch (Exception e) {
            System.out.println(e + " in Helper class");
            throw e;
        }
    }

    // REQUEST PARAMs SECURITY CHECK HELPERS

    enum allowedPathParams {PRICE,ProductName}
    enum restrictedPathParams {create, delete, update}
    enum ProductOrderParam{price, ProductName}

    private static boolean isAllowed(String pathParam) {
        if (allowedPathParams.valueOf(pathParam) != null
                && restrictedPathParams.valueOf(pathParam) == null)
            return true;
        else
            return false;
    }
}
