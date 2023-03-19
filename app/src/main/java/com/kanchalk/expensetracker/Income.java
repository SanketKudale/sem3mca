package com.kanchalk.expensetracker;

import android.os.Parcel;
import android.os.Parcelable;

public class Income implements Parcelable {
    String amount;
    String date;
    String from;
    String description;
    String incomeSource;
    String incomeType;
    public Income(){}
    public Income(String amount, String date, String from, String description, String incomeSource, String incomeType) {
        this.amount = amount;
        this.date = date;
        this.from = from;
        this.description = description;
        this.incomeSource = incomeSource;
        this.incomeType = incomeType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }

    public String getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(String incomeType) {
        this.incomeType = incomeType;
    }


    protected Income(Parcel in) {
        amount = in.readString();
        date = in.readString();
        from = in.readString();
        description = in.readString();
        incomeSource = in.readString();
        incomeType = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(date);
        dest.writeString(from);
        dest.writeString(description);
        dest.writeString(incomeSource);
        dest.writeString(incomeType);
    }

    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Income> CREATOR = new Parcelable.Creator<Income>() {
        @Override
        public Income createFromParcel(Parcel in) {
            return new Income(in);
        }
        @Override
        public Income[] newArray(int size) {
            return new Income[size];
        }
    };
}