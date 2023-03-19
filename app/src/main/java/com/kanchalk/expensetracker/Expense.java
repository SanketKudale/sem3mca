package com.kanchalk.expensetracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Expense implements Parcelable {
    String expenseAmount;
    String expenseDate;
    String expenseType;
    String expenseMode;
    String description;
    public String getExpenseAmount() {
        return expenseAmount;
    }

    public Expense(String expenseAmount, String expenseDate, String expenseMode, String description,String expenseType) {
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.expenseType = expenseType;
        this.expenseMode = expenseMode;
        this.description = description;
    }
    public Expense(){}

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getExpenseMode() {
        return expenseMode;
    }

    public void setExpenseMode(String expenseMode) {
        this.expenseMode = expenseMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    protected Expense(Parcel in) {
        expenseAmount = in.readString();
        expenseDate = in.readString();
        expenseMode = in.readString();
        description = in.readString();
        expenseType = in.readString();

    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(expenseAmount);
        dest.writeString(expenseDate);
        dest.writeString(expenseMode);
        dest.writeString(description);
        dest.writeString(expenseType);
    }

    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Expense> CREATOR = new Parcelable.Creator<Expense>() {
        @Override
        public Expense createFromParcel(Parcel in) {
            return new Expense(in);
        }
        @Override
        public Expense[] newArray(int size) {
            return new Expense[size];
        }
    };
}
