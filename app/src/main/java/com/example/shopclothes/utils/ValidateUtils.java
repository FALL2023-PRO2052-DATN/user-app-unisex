package com.example.shopclothes.utils;

public class ValidateUtils {
    public static boolean validateLoginIsEmpty(String email, String pass){
        return !email.isEmpty() && !pass.isEmpty();
    }

    public static boolean validateForgotPassIsEmpty(String email){
        return !email.isEmpty();
    }

    public static boolean validateRegisterIsEmpty(String email, String pass, String againPass){
        return !email.isEmpty() && !pass.isEmpty() && !againPass.isEmpty();
    }
    public static boolean validateRegisterEqual(String pass, String againPass){
        return againPass.equals(pass);
    }
}
