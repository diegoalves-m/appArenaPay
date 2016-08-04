package com.developer.diegoalves.peladapay.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Diego Alves on 24/11/2015.
 */
public class Player implements Parcelable {

    private long id;
    private String name;
    private double amountPaid;
    private int isPaid;

    public Player() {
    }

    public Player(long id, String name, double amountPaid, int isPaid) {
        this.id = id;
        this.name = name;
        this.amountPaid = amountPaid;
        this.isPaid = isPaid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    public enum IsPaid {
        paid, notPaid
    }

    public Player(Parcel parcel) {
        id = parcel.readLong();
        name = parcel.readString();
        amountPaid = parcel.readDouble();
        isPaid = parcel.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getName());
        dest.writeDouble(getAmountPaid());
        dest.writeInt(getIsPaid());
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountPaid=" + amountPaid +
                ", isPaid=" + isPaid +
                '}';
    }
}
