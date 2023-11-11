package com.example.shopclothes.view.activity.comment.addComment;

import com.example.shopclothes.model.ProductComment;

import java.util.List;

public interface AddCommentContract {
    interface View {
        void onClick();
        void initPresenter();
        void onListProduct(List<ProductComment> list);
        void onMessage(String message, boolean check);
    }
    interface Presenter {
        void getListProduct(String id);
        void insertComment(double pointRating, String content, String idUser, int idProduct);
    }
}
