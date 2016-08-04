package com.developer.diegoalves.peladapay.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.diegoalves.peladapay.entities.Values;


/**
 * Created by Diego Alves on 25/11/2015.
 */
public class ValuesRepository extends DataBaseHelper {

    SQLiteDatabase db;

    public ValuesRepository(Context context) {
        super(context);
    }

    public void insertAndUpdate(Values val) {
        db = getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.ALL, val.all);
            values.put(DataBaseHelper.CURRENT, val.getCurrent());
            values.put(DataBaseHelper.VALUE_M, val.getValueM());
            values.put(DataBaseHelper.VALUE_P, val.getValueP());
            if (results() == 0) {
                db.insert(DataBaseHelper.TABLE_VALUE, "", values);
            } else {
                String _id = String.valueOf(1);
                String[] whereArgs = new String[]{_id};
                db.update(DataBaseHelper.TABLE_VALUE, values, "_id = ?", whereArgs);
            }
        } finally {
            db.close();
        }
    }

    public double getCurrent() {
        db = getWritableDatabase();
        Cursor cursor;
        double r = 0;
        cursor = db.rawQuery("select current from value", null);

        if (cursor.moveToNext()) {
            r = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.CURRENT));
        }
        cursor.close();
        return r;
    }


    public double getValueMonth() {
        db = getWritableDatabase();
        Cursor cursor;
        double r = 0;
        cursor = db.rawQuery("select value_m from value", null);

        if (cursor.moveToNext()) {
            r = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.VALUE_M));
        }
        cursor.close();
        return r;
    }

    public double getValuePlayer() {
        db = getWritableDatabase();
        Cursor cursor;
        double r = 0;
        cursor = db.rawQuery("select value_p from value", null);

        if (cursor.moveToNext()) {
            r = cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.VALUE_P));
        }
        cursor.close();
        return r;
    }

    public int results() {
        db = getWritableDatabase();
        Cursor cursor = db.query(DataBaseHelper.TABLE_VALUE, null, null, null, null, null, null);
        int cont = 0;

        while (cursor.moveToNext()) {
            cont++;
        }
        cursor.close();
        return cont;
    }
}
