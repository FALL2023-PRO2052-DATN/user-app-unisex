package com.example.shopclothes.view.fragment.homeFragment.response;

import com.example.shopclothes.model.Banner;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBanner {
    private String status;
    @SerializedName("results")
    private List<Banner> banner;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }
}
