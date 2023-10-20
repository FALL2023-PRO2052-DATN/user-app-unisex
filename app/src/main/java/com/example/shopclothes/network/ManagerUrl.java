package com.example.shopclothes.network;

public class ManagerUrl {
    public static final String BASE_URL = "http://192.168.1.14:3000/api/";

    // user
    public static final String INSERT_USER = "insert-user";
    // banner
    public static final String READ_BANNER = "read-banner";
    // product
    public static final String READ_PRODUCT_NEW = "read-new-product";
    public static final String READ_PRODUCT_OUTSTANDING = "read-outstanding-product";
    public static final String READ_PRODUCT_ALL = "read-all-product";
    public static final String READ_PRODUCT_ID_CATEGORY = "read-byId-category-product";
    public static final String READ_PRODUCT_ID = "read-byId-product";
    // type product
    public static final String READ_TYPE_PRODUCT = "read-type-product";

    // comment
    public static final String READ_COMMENT_ID = "read-byId-product-comment";
    // size
    public static final String READ_SIZE_ID_PRODUCT = " read-byId-product-size";

}
