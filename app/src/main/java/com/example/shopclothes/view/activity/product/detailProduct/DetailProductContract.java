package com.example.shopclothes.view.activity.product.detailProduct;

import com.example.shopclothes.model.Cart;
import com.example.shopclothes.model.Comment;
import com.example.shopclothes.model.Product;
import com.example.shopclothes.model.Size;

import java.util.List;

public interface DetailProductContract {
    interface View{
         void onClick();
         void initPresenter();
         void onProduct(Product product);
         void onListComment(List<Comment> list);
         void onListSizeByIdProduct(List<Size> list);
         void onListProductByIdCategory(List<Product> list);
         void onListCartByIdUser(List<Cart> list);
         float averageRating(List<Comment> list);
         void seeMore();
         void openBottomSheetDialogFragment(Product product, List<Size> list);

    }
    interface Presenter{
        void getProduct(int id);
        void getListCommentById(int id);
        void getListSizeByIdProduct(int id);
        void getListProductByIdCategory(int id);
        void getListCartByIdUser(String id);
    }
}
