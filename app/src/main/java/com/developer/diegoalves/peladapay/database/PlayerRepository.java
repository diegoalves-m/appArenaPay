package com.developer.diegoalves.peladapay.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.developer.diegoalves.peladapay.entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diego Alves on 24/11/2015.
 */
public class PlayerRepository extends DataBaseHelper {

    SQLiteDatabase db;

    public PlayerRepository(Context context) {
        super(context);
    }

    public Long insertAndUpdate(Player player) {
        db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.NAME, player.getName());
            values.put(DataBaseHelper.AMOUNT_PAID, player.getAmountPaid());
            values.put(DataBaseHelper.IS_PAID, player.getIsPaid());
            if(player.getId() == 0) {
                db.insert(DataBaseHelper.TABLE_PLAYER, "", values);
                return player.getId();
            } else {
                String _id = String.valueOf(player.getId());
                String[] whereArgs = new String[]{_id};
                db.update(DataBaseHelper.TABLE_PLAYER, values, "_id = ?", whereArgs);
            }
        } finally {
            db.close();
        }
        return player.getId();
    }

    public List<Player> listAll() {
        db = getWritableDatabase();

        try {
            Cursor cursor = db.query(DataBaseHelper.TABLE_PLAYER, null, null, null, null, null, null);
            return toList(cursor);
        } finally {
            db.close();
        }
    }

    public List<Player> toList(Cursor cursor) {
        List<Player> players = new ArrayList<>();
        while (cursor.moveToNext()) {
            Player player = new Player();
            player.setName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.NAME)));
            player.setAmountPaid(cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.AMOUNT_PAID)));
            player.setId(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.ID)));
            player.setIsPaid(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.IS_PAID)));
            players.add(player);
        }
        return players;
    }

    public void delete(Player p) {
        db = getWritableDatabase();
        try {
            db.delete(DataBaseHelper.TABLE_PLAYER, DataBaseHelper.ID + " = ?", new String[]{String.valueOf(p.getId())});
        } finally {
            db.close();
        }

    }
}
