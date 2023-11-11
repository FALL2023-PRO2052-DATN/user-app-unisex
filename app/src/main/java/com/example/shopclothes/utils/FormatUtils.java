package com.example.shopclothes.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
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

        formattedPrice = formattedPrice.replace("₫", "").replaceAll("\\.", "").trim().replaceAll(",", ".");
        double price = 0;
        try {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            price = numberFormat.parse(formattedPrice).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return price;
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

    public static String formatRating(double number){
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return decimalFormat.format(number).replace(",", ".");

    }
}
