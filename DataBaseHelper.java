package com.example.friendscom;

import android.arch.lifecycle.ViewModelProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.print.PrinterId;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME ="EDMTDEV";

    private static final String TABLE_NAME="Persons";
    private static final String KEY_ID ="Id";
    private static final String KEY_Email ="Name";
    private static final String KEY_Name ="Name";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null , DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME+"("
                + KEY_ID+ " INTEGER PRIMARY KEY," + KEY_Name+"TEXT,"
                +KEY_Email+ " TEXT"+")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);

    }


    public void addPerson (Person person)
    {

        SQLiteDatabase database= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Name, person.getName());
        values.put(KEY_Email, person.getEmail());

        database.insert(TABLE_NAME,null, values);
        database.close();

    }

    public int updatePerson(Person person){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_Name,person.getName());
        values.put(KEY_Email,person.getEmail());


        return database.update(TABLE_NAME,values,KEY_ID+"=?", new String[]{String.valueOf(person.getId())});
    }

    public void deletePerson (Person person){

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, KEY_ID+" =?", new String[]{String.valueOf(person.getId())});
        database.close();
    }

    public Person getPerson (int id) {
        SQLiteDatabase database =this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME,new String[]{KEY_ID,KEY_Name, KEY_Email}, KEY_ID+" =?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor !=null)
            cursor.moveToFirst();
        return new Person(cursor.getInt(0),cursor.getString(1), cursor.getString(2));



    }

    public List<Person> getAllPerson (){

        List<Person> lstPersons = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Person person= new Person();
                person.setId(cursor.getInt(0));
                person.setName(cursor.getString(1));
                person.setEmail(cursor.getString(2));

                lstPersons.add(person);

            }
            while (cursor.moveToNext());

        }
        return lstPersons;
    }

}
