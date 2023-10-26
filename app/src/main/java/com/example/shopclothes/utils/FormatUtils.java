package com.example.shopclothes.utils;

import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {
    public static String formatCurrency(double price) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedPrice = currencyFormat.format(price);
        formattedPrice = formattedPrice.replace("₫", "").trim() + "₫";
        return formattedPrice.replaceAll("\\s", "");
    }

    public static double parseCurrency(String formattedPrice) {
        // Loại bỏ ký tự "₫" cuối cùng
        formattedPrice = formattedPrice.replace("₫", "");
        // Loại bỏ tất cả các khoảng trắng
        formattedPrice = formattedPrice.replaceAll("\\s", "");
        // Định dạng chuỗi thành giá trị số
        double parsedPrice = 0;
        try {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            parsedPrice = numberFormat.parse(formattedPrice).doubleValue();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return parsedPrice;
    }

    public static String formatDate(Date date) {
        // Định dạng ngày theo kiểu "dd/mm/yyyy TT"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", new Locale("vi", "VN"));
        return dateFormat.format(date);
    }

    public static String formatID(){
        String uni = "UNI";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());
        return uni+timestamp;
    }
}
