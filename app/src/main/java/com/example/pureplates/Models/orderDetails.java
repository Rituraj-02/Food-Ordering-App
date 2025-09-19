package com.example.pureplates.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class orderDetails implements Serializable {
    public String userUid = null;
    public String userName = null;
    public List<String> foodNames = null;
    public List<String> foodImages = null;
    public List<String> foodPrices = null;
    public List<Integer> foodQuantities = null;
    public String address = null;
    public String totalPrice = null;
    public String phoneNumber = null; // fixed typo: removed space
    public boolean orderAccepted = false;
    public boolean paymentReceived = false;
    public String itemPushKey = null;
    public long currentTime = 0L;

    public orderDetails() {
    }

    public orderDetails(Parcel in) {
        userUid = in.readString();
        userName = in.readString();
        foodNames = in.createStringArrayList();
        foodImages = in.createStringArrayList();
        foodPrices = in.createStringArrayList();
        address = in.readString();
        totalPrice = in.readString();
        phoneNumber = in.readString();
        orderAccepted = in.readByte() != 0;
        paymentReceived = in.readByte() != 0;
        itemPushKey = in.readString();
        currentTime = in.readLong();
    }

    public static final Parcelable.Creator<orderDetails> CREATOR = new Parcelable.Creator<orderDetails>() {
        @Override
        public orderDetails createFromParcel(Parcel in) {
            return new orderDetails(in);
        }

        @Override
        public orderDetails[] newArray(int size) {
            return new orderDetails[size];
        }
    };


    public int describeContents() {
        return 0;
    }


    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(userUid);
        parcel.writeString(userName);
        parcel.writeStringList(foodNames);
        parcel.writeStringList(foodImages);
        parcel.writeStringList(foodPrices);
        parcel.writeString(address);
        parcel.writeString(totalPrice);
        parcel.writeString(phoneNumber);
        parcel.writeByte((byte) (orderAccepted ? 1 : 0));
        parcel.writeByte((byte) (paymentReceived ? 1 : 0));
        parcel.writeString(itemPushKey);
        parcel.writeLong(currentTime);
    }
}
