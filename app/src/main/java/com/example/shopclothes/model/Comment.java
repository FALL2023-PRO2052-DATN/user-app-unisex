package com.example.shopclothes.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment {
    @SerializedName("ho_va_ten")
    private String nameUser;
    @SerializedName("anh_dai_dien")
    private String avatarUser;
    @SerializedName("ngay_danh_gia")
    private Date dateComment;
    @SerializedName("diem_danh_gia")
    private Float pointComment;
    @SerializedName("noi_dung")
    private String contentComment;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getAvatarUser() {
        return avatarUser;
    }

    public void setAvatarUser(String avatarUser) {
        this.avatarUser = avatarUser;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

    public Float getPointComment() {
        return pointComment;
    }

    public void setPointComment(Float pointComment) {
        this.pointComment = pointComment;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "nameUser='" + nameUser + '\'' +
                ", avatarUser='" + avatarUser + '\'' +
                ", dateComment='" + dateComment + '\'' +
                ", pointComment=" + pointComment +
                ", contentComment='" + contentComment + '\'' +
                '}';
    }
}
