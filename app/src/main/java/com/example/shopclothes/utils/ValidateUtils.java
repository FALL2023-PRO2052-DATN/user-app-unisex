package com.example.shopclothes.utils;

public class ValidateUtils {
    public static final int MIN_PASSWORD_LENGTH = 6;
    private static final String DATE_REGEX = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

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
    public static boolean isDataInputEmpty(String... datas) {
        for (String str : datas) {
                if (str == null || str.isEmpty()) {
                    return true;
                }
        }
        return false;
    }

}
