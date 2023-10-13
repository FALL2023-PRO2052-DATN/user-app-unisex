package com.example.shopclothes.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    public static String formatCurrency(double price) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedPrice = currencyFormat.format(price);
        formattedPrice = "₫" + formattedPrice.replace("₫", "").trim();
        return formattedPrice;
    }
}
