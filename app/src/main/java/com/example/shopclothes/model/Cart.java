package com.example.shopclothes.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Cart implements Parcelable {
    private int id;
    @SerializedName("ten_san_pham")
    private String name;
    @SerializedName("anh_dai_dien")
    private String image;
    @SerializedName("giam_gia")
    private int discount;
    @SerializedName("so_luong")
    private int quantity;
    @SerializedName("don_gia")
    private double price;
    @SerializedName("kich_thuoc")
    private String size;
    @SerializedName("san_pham_id")
    private int idProduct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }


    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", price=" + price +
                ", size='" + size + '\'' +
                ", idProduct=" + idProduct +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeInt(discount);
        parcel.writeInt(quantity);
        parcel.writeDouble(price);
        parcel.writeString(size);
        parcel.writeInt(idProduct);
    }

    protected Cart(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        discount = in.readInt();
        quantity = in.readInt();
        price = in.readDouble();
        size = in.readString();
        idProduct = in.readInt();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel in) {
            return new Cart(in);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
