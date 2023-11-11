package com.example.shopclothes.view.activity.comment.seeComment;

import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.Size;

import java.util.List;

public interface SeeCommentContract {
    interface View{
         void onClick();
         void initPresenter();
         void onListComment(List<Comment> list);
         float averageRating(List<Comment> list);

    }
    interface Presenter{
        void getListCommentById(int id);
    }
}
