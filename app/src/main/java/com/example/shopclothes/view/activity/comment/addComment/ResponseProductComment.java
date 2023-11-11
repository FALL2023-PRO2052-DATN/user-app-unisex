package com.example.shopclothes.view.activity.comment.addComment;

import com.example.shopclothes.model.ProductComment;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseProductComment {
    private String status;
    @SerializedName("productList")
    private List<ProductComment> productCommentList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProductComment> getProductCommentList() {
        return productCommentList;
    }

    public void setProductCommentList(List<ProductComment> productCommentList) {
        this.productCommentList = productCommentList;
    }
}
