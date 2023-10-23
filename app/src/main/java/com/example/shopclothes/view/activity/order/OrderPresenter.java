package com.example.shopclothes.view.activity.order;


public class OrderPresenter implements OrderContract.Presenter {
    private OrderContract.View mView;

    public OrderPresenter(OrderContract.View mView) {
        this.mView = mView;
    }


}
