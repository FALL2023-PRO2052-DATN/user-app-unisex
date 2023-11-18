package com.example.shopclothes.network;

public class ManagerUrl {

    public static final String BASE_URL = "http://172.16.51.141:3000/api/";
    public static final String BASE_URL_STRIPE = "https://api.stripe.com/v1/";

    // stripe
    public static final String CUSTOMER = "customers";
    public static final String EPHEMERAL_KEY = "ephemeral_keys";
    public static final String PAYMENT_INTENTS = "payment_intents";


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
    public static final String READ_PRODUCT_COMMENT = "read-comment-bill";

    // type product
    public static final String READ_TYPE_PRODUCT = "read-type-product";

    // comment
    public static final String READ_COMMENT_ID = "read-byId-product-comment";
    public static final String INSERT_COMMENT = "insert-comment";
    // size
    public static final String READ_SIZE_ID_PRODUCT = "read-byId-product-size";

    // cart
    public static final String READ_CART_BY_ID_USER = "read-byId-user-cart";
    public static final String READ_CART = "read-cart";
    public static final String INSERT_CART = "insert-cart";
    public static final String DELETE_CART = "delete-cart";
    public static final String UPDATE_CART = "update-cart";

    // address
    public static final String READ_ADDRESS = "read-address";
    public static final String GET_LIST_ADDRESS = "get-list-address";
    public static final String INSERT_ADDRESS = "insert-address";
    public static final String UPDATE_ADDRESS = "update-address";
    public static final String DELETE_ADDRESS = "delete-address";

    // discount
    public static final String READ_DISCOUNT_BY_ID = "read-byId-discount";

    // order
    public static final String INSERT_ORDER = "insert-order";
    public static final String INSERT_DETAIL_ORDER = "insert-detail-order";

    // bill
    public static final String READ_BILL = "read-byId-status-bill";
    public static final String CANCEL_BILL = "cancel-bill";
    public static final String READ_DETAIL_BILL = "read-detail-bill";

    //setting
    public static final String UPDATE_INF_USER = "update-user";
    public static final String READ_USER = "read-user";
}
