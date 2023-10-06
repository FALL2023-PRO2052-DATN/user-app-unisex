package com.example.shopclothes.utils;

public class ValidateUtils {
    public static boolean validateLogin(String email, String pass){
        return !email.isEmpty() && !pass.isEmpty();
    }

    public static boolean validateForgotPass(String email){
        return !email.isEmpty();
    }
}
