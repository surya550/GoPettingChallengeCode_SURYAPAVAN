package com.suryapavan.gopetting.challengecode.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
/**
 * Created by surya on 4/4/2017.
 */



public class DatabaseAccess {
    private DatabaseHelper helper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    public static final String[] tableNames = {"cart"};


    private DatabaseAccess(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = helper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public ArrayList<DataCartObject> getCartAllItems(String TableName) {
        ArrayList<DataCartObject> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TableName, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DataCartObject d = new DataCartObject();
            d.setId(cursor.getInt(0));
            d.setName(cursor.getString(1));
            d.setEndDate(cursor.getString(2));
            d.setIcon(cursor.getString(3));
            d.setQuantity(cursor.getInt(4));
            list.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }




    public void deleteItem(String TableName,int Id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TableName, "Id  = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

    public void deleteAllData(String TableName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(TableName,null,null );
        db.close();
    }


    public long insertItem(String TableName, String Name,String EndDate,String Icon,int Quantity) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", Name);
        values.put("EndDate", EndDate);
        values.put("Icon", Icon);
        values.put("Quantity", Quantity);

        // updating row
        return db.insert(TableName,null,values);
    }


    public ArrayList<DataCartObject> getCartItem(String TableName,String Name) {
        ArrayList<DataCartObject> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TableName +" WHERE " +"Name"+" = '"+ Name +"'" , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DataCartObject d = new DataCartObject();
            d.setId(cursor.getInt(0));
            d.setName(cursor.getString(1));
            d.setEndDate(cursor.getString(2));
            d.setIcon(cursor.getString(3));
            d.setQuantity(cursor.getInt(4));
            list.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public int updateItem(String TableName, String Name, int Quantity) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Quantity", Quantity);
        // updating row
        return db.update(TableName, values, "Name = '" + Name +"'", null);
    }


}
