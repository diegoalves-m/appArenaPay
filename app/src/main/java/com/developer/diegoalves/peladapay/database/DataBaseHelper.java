package com.developer.diegoalves.peladapay.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Diego Alves on 24/11/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "arenaPlay";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_VALUE = "value";

    public static final String ALL = "_all";
    public static final String CURRENT = "current";
    public static final String VALUE_P = "value_p";
    public static final String VALUE_M = "value_m";

    public static final String TABLE_PLAYER = "players";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String AMOUNT_PAID = "paid";
    public static final String IS_PAID = "is_paid";


    public DataBaseHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PLAYER + "(" +
                ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                NAME +" TEXT NOT NULL," +
                AMOUNT_PAID + " DOUBLE NOT NULL," +
                IS_PAID + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_VALUE + "(" +
                ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ALL +" DOUBLE NOT NULL," +
                CURRENT +" DOUBLE NOT NULL," +
                VALUE_P +" DOUBLE NOT NULL," +
                VALUE_M +" DOUBLE NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
