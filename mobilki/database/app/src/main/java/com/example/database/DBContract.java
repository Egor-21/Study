package com.example.database;

import android.provider.BaseColumns;

public class DBContract {
    public static class DBEntry implements BaseColumns {
        public static final String TABLE_NAME = "people";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE = "phone";

        public static final String COLUMN_NAME_CITY = "city";
    }

}
