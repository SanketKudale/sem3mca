package com.kanchal.expensetracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Savings implements Parcelable {
    String savingsAmount;
    String savingsDescription;
    String savingsFor;
    String savingsDate;
    public String getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(String savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public String getSavingsDescription() {
        return savingsDescription;
    }

    public void setSavingsDescription(String savingsDescription) {
        this.savingsDescription = savingsDescription;
    }

    public String getSavingsFor() {
        return savingsFor;
    }

    public void setSavingsFor(String savingsFor) {
        this.savingsFor = savingsFor;
    }

    public Savings(String savingsAmount, String savingsDescription, String savingsFor, String savingsDate) {
        this.savingsAmount = savingsAmount;
        this.savingsDescription = savingsDescription;
        this.savingsFor = savingsFor;
        this.savingsDate = savingsDate;
    }

    public String getSavingsDate() {
        return savingsDate;
    }

    public void setSavingsDate(String savingsDate) {
        this.savingsDate = savingsDate;
    }

    public Savings(){}

    protected Savings(Parcel in) {
        savingsAmount = in.readString();
        savingsDate = in.readString();
        savingsFor = in.readString();
        savingsDescription = in.readString();


    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(savingsAmount);
        dest.writeString(savingsDate);
        dest.writeString(savingsFor);
        dest.writeString(savingsDescription);

    }

    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Savings> CREATOR = new Parcelable.Creator<Savings>() {
        @Override
        public Savings createFromParcel(Parcel in) {
            return new Savings(in);
        }
        @Override
        public Savings[] newArray(int size) {
            return new Savings[size];
        }
    };

}

