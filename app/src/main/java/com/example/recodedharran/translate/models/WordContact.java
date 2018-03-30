package com.example.recodedharran.translate.models;

import android.provider.BaseColumns;

/**
 * Created by Recodedharran on 13.1.2018.
 */

public class WordContact {
    public static class WordEntry implements BaseColumns
    {

        public static final String TABLE_NAME="words";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_SELECT="word";
        public static final String ID=BaseColumns._ID;
        public static final String COLUMN_DEFINITION="definition";
        public static final String COLUMN_SYNONYM="synonym";

        public static final String SQL_CREATE_WORDS_TABLE =  "CREATE TABLE " + WordContact.WordEntry.TABLE_NAME + " ("
                + WordContact.WordEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordEntry.COLUMN_SELECT + " TEXT, "
                + WordEntry.COLUMN_SYNONYM + " TEXT, "
                + WordEntry.COLUMN_DEFINITION + " TEXT);";

    }
}
