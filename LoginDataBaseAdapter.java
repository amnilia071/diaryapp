package com.spark.android.personaltimeline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

public class LoginDataBaseAdapter
{
    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;
    public static final String KEY_BODY = "body";
    public static final String KEY_ROWID = "date";
    private static final String DATABASE_TABLE="LOGIN";



    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table "+"LOGIN"+
            "( " +"ID"+" integer primary key autoincrement,"+ "PASSWORD text," + "HINT text);";
           /* "create table"+ "BODY"+ "("+"DATE text,"+ "BODY text"+")" + ";"*/
    static final String CREATE_BODY =
            " create table " + "BODY" +"("+"DATE text," +" BODY text"+")";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DataBaseHelper dbHelper;
    public LoginDataBaseAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public  LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String password, String hint)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("PASSWORD",password);
        newValues.put("HINT",hint);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "AlarmActivity Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public String getSinlgeEntry(String password1)
    {
        Cursor cursor=db.query("LOGIN", null, " PASSWORD=?", new String[]{password1}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }
    public void  updateEntry(String password)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.

        updatedValues.put("PASSWORD",password);

        String where="PASSWORD = ?";
        db.update("LOGIN",updatedValues, where, new String[]{password});
    }

    //to get hint from the Login db
    public String getHint() {
        String result="";
        String hint = "SELECT HINT FROM LOGIN";
        Cursor cursor = db.rawQuery(hint, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        return result;
    }

    public String getNotesBody(String date){

        String result="";
        Cursor cursor=db.query("BODY", null, " DATE=?", new String[]{date}, null, null, null);
             if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

               result= cursor.getString(cursor.getColumnIndex("BODY"));
            } while (cursor.moveToNext());
        }

        return result;
    }

    public void insertNote(String date,String body){

//look for the date in the database
        ContentValues values = new ContentValues();
        values.put("DATE",date);
        values.put("BODY",body);
        db.insert("BODY", null, values);


      /*  Cursor cursor=db.query("BODY", null, " DATE=?", new String[]{date}, null, null, null);


        if(cursor.getCount()<1) // UserName Not Exist
        {

            db.insert("BODY",null,values);
            cursor.close();

        }
        cursor.moveToFirst();
        db.update("BODY", values, "DATE" + "=" + date, null);
        cursor.close();*/



           // Inserting Row



    }

}










