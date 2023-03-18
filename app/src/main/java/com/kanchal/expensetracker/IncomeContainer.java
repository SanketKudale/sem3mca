package com.kanchal.expensetracker;

import android.provider.BaseColumns;

public final class IncomeContainer {

    private IncomeContainer() {
    }
    public static class IncomeTable implements BaseColumns {
        public static final String TABLE_NAME = "IncomeData";
        public static final String COLUMN_AMOUNT = "amountofIncome";
        public static final String COLUMN_DATE = "dateOfIncome";
        public static final String COLUMN_FRM = "frm";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_INCOMESOURCE = "source";
        public static final String COLUMN_INCOMETYPE = "typeOfIncome";
    }
}