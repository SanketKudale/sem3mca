package com.kanchal.expensetracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransAdapter extends ArrayAdapter<AllTrans> {

    ArrayList<AllTrans> list1;
    Context context;

    public TransAdapter(ArrayList<AllTrans> list1, Context context) {
        super(context, 0, list1);
        this.list1 = list1;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View transcard = convertView;
        if (transcard == null)
            transcard = LayoutInflater.from(context).inflate(R.layout.trans_card, parent, false);

        TextView title = transcard.findViewById(R.id.trans_title);
        TextView amt = transcard.findViewById(R.id.trans_amt);
        TextView date = transcard.findViewById(R.id.trans_date);


            try {


                AllTrans allTrans = list1.get(position);


                if (position > 0) {
                    if (dateDiff(allTrans.getDate(), list1.get(position - 1).getDate()) > 0) {
                        date.setText(String.valueOf(DateFormat.getDateInstance(DateFormat.MEDIUM).format(allTrans.getDate())));
                        date.setVisibility(View.VISIBLE);
                    } else {
                        date.setVisibility(View.GONE);
                    }

                } else {

                    date.setText(String.valueOf(DateFormat.getDateInstance(DateFormat.MEDIUM).format(allTrans.getDate())));
                    date.setVisibility(View.VISIBLE);
                }

                title.setText(allTrans.getTitle());

                if (allTrans.getType().equals("debit")) {
                    amt.setText("- ₹" + allTrans.getAmt());
                    amt.setTextColor(Color.RED);
                } else {
                    amt.setText("+ ₹" + allTrans.getAmt());
                    amt.setTextColor(Color.GREEN);
                }
            } catch (Exception e) {

            }


        return transcard;
    }

    private int dateDiff(Date date1, Date date2) {
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        return Integer.parseInt(Long.toString(differenceDates));
    }
}
