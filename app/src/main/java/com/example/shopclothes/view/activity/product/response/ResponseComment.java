package com.example.shopclothes.view.activity.product.response;

import com.example.shopclothes.model.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseComment {
    private String status;
    @SerializedName("commentList")
    private List<Comment> commentList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
