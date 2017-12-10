package com.example.jamesbrowning.countlesspies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_Name = "players.db";
    public static final String TABLE_PLAYERS = "players";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PLAYERNAME = "playername";
    public static final String COLUMN_SCORE = "score";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_Name, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PLAYERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLAYERNAME + " TEXT," + COLUMN_SCORE + " INT" +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        onCreate(db);
    }

    // Add a new row to the database
    public void addPlayer(Player player) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYERNAME, player.get_playername());
        values.put(COLUMN_SCORE, player.get_score());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PLAYERS, null, values);
        db.close();
    }

    // Delete all players from the database
    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_PLAYERS + " WHERE 1;";
        db.execSQL(query);
    }

    // Delete a player from the database
    public void deletePlayer(String playerName) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_PLAYERNAME + "=\"" + playerName + "\";");
    }

    // Print out the database as a string
    public String databaseToString() {
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PLAYERS + " WHERE 1;";

        // Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        // Move to the first row in your results
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_PLAYERNAME))!=null) {
                dbString += c.getString(c.getColumnIndex(COLUMN_PLAYERNAME));
                dbString += ": ";
                dbString += c.getString(c.getColumnIndex(COLUMN_SCORE));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }
}
