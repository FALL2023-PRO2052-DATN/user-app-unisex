package com.example.shopclothes.view.activity.comment.seeComment;
import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Comment;

import java.util.List;

public interface SeeCommentContract {
    interface View{
         void onClick();
         void initPresenter();
         void onListComment(List<Comment> list);
         float averageRating(List<Comment> list);
         void showPopupMenu();
        void onListCartByIdUser(List<Cart> cartList);

    }
    interface Presenter{
        void getListCommentById(int id);
        void getListCommentByStart(int id,int start);
        void readListCartByIdUser(String id);
    }
}
