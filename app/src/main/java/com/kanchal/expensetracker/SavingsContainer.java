package com.kanchal.expensetracker;

import android.provider.BaseColumns;

public final class SavingsContainer {

    private SavingsContainer() {
    }
    public static class SavingsTable implements BaseColumns {
        public static final String TABLE_NAME = "SavingsData";
        public static final String COLUMN_AMOUNT = "amountofSavings";
        public static final String COLUMN_DATE = "dateOfSavings";
        public static final String COLUMN_FOR = "forSavings";
        public static final String COLUMN_DESCRIPTION = "descriptionSavings";
    }
}
