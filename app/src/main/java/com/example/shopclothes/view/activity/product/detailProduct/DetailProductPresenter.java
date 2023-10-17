package com.example.shopclothes.view.activity.product.detailProduct;

public class DetailProductPresenter implements DetailProductContract.Presenter {
    private DetailProductContract.View view;

    public DetailProductPresenter(DetailProductContract.View view) {
        this.view = view;
    }

    @Override
    public void getProduct(int id) {

    }
}
