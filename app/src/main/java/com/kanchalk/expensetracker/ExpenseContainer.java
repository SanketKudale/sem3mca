package com.kanchalk.expensetracker;

import android.provider.BaseColumns;

public final class ExpenseContainer {
    private ExpenseContainer() {
    }
    public static class ExpenseTable implements BaseColumns {
        public static final String TABLE_NAME = "ExpenseData";
        public static final String COLUMN_AMOUNT = "amountofExpense";
        public static final String COLUMN_DATE = "dateOfExpense";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_EXPENSEMODE = "expensemode";
        public static final String COLUMN_EXPENSETYPE = "expensetype";
    }
}