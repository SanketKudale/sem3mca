package com.kanchal.expensetracker;

import java.util.Date;

public class AllTrans {

    String title,amt,type;
    Date date;

    public AllTrans() {
    }

    public AllTrans(String title, String amt, String type, Date date) {
        this.title = title;
        this.amt = amt;
        this.type = type;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
