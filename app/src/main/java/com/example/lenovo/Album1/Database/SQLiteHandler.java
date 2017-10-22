package com.example.lenovo.Album1.Activity.recyclerview.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api_movie";

    // Login table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_MESSAGE = "message";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CART_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_CART_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_COUNT = "count";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DATE = "date";
    private static final String KEY_POS= "pos";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_M_EMAIL = "email";
    private static final String KEY_M_DATE = "date";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_MOVIE_ID = "movie_id";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    // Creating Tabl
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";

         String Create_CART_Table=
                "CREATE TABLE " + TABLE_CART + "("
                        + KEY_CART_ID + " INTEGER PRIMARY KEY,"
                        + KEY_CART_NAME + " TEXT,"
                        + KEY_PRICE + " TEXT,"
                        + KEY_COUNT + " INTEGER,"
                        + KEY_DATE + " TEXT,"
                        + KEY_IMAGE + " TEXT"
                        + ")";

        String Create_MESSAGE_Table=
                "CREATE TABLE " + TABLE_MESSAGE + "("
                        + KEY_CART_ID + " INTEGER PRIMARY KEY,"
                        + KEY_USERNAME + " TEXT,"
                        + KEY_M_EMAIL + " TEXT,"
                        + KEY_M_DATE + " DATE,"
                        + KEY_MOVIE_ID + " TEXT,"
                        + KEY_POS + " INTEGER,"
                        + KEY_MESSAGE + " TEXT"
                        + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(Create_CART_Table);
        db.execSQL(Create_MESSAGE_Table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }

    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    public  void addCart(Movie movie) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CART_ID,movie.getMovie_ID());
        values.put(KEY_CART_NAME, movie.getName()); // Name
        values.put(KEY_PRICE, movie.getPrice()); // Email
        values.put(KEY_DATE,movie.getDate()); // Created At
        values.put(KEY_IMAGE, movie.getTumbnail()); // Created At
        values.put(KEY_COUNT,movie.getCount()); // Created At

        long id = db.insert(TABLE_CART, null, values);
        db.close(); // Closing database connection
    }


    public void addMessage(String username,String email,String message,String movie_id,int pos,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME  , username); // Name
        values.put(KEY_M_EMAIL  , email); // Email
        values.put(KEY_M_DATE  ,date); // Created At
        values.put(KEY_MOVIE_ID , movie_id); // Created At
        values.put(KEY_MESSAGE  ,message); // Created At
        values.put(KEY_POS,pos); // Created At


        // Inserting Row
        long id = db.insert(TABLE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }


    public List<Message> getMessage(int pos,String id) {
       // Log.i("pos", String.valueOf(pos));
       // Log.i("id",id);
        List<Message> messageList = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " where " + KEY_POS+ " ="+ pos +" AND  " + KEY_MOVIE_ID + " = "+id;
        //Log.i("list",selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
                message.setDate(cursor.getString(cursor.getColumnIndex(KEY_M_DATE)));
                message.setMessage(cursor.getString(cursor.getColumnIndex(KEY_MESSAGE)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return messageList;
    }


    public List<Movie> getCartMovie() {
        List<Movie> movieList = new ArrayList<Movie>();
        String selectQuery = "SELECT  * FROM " + TABLE_CART ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Movie cheque = new Movie();
                cheque.setMovie_ID(cursor.getString(cursor.getColumnIndex(KEY_CART_ID)));
                cheque.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                cheque.setTumbnail(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                cheque.setPrice(cursor.getString(cursor.getColumnIndex(KEY_PRICE)));
                cheque.setCount(cursor.getInt(cursor.getColumnIndex(KEY_COUNT)));
                cheque.setName(cursor.getString(cursor.getColumnIndex(KEY_CART_NAME)));
                movieList.add(cheque);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return movieList;
    }
    public Boolean getSerial(String serial) {
        List<Movie> moviesList = new ArrayList<Movie>();
        String selectQuery = "select name from cart where name= '"+serial+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                // cheque.setCheck_ID(cursor.getInt(cursor.getColumnIndex(CHEQUE_ID)));
                movie.setDate(cursor.getString(cursor.getColumnIndex(KEY_CART_NAME)));
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }
        if (moviesList.isEmpty()) {
            cursor.close();
            db.close();
            return false;
        }else
            return true;
    }

    public int deleteCart(String id) {
       // Log.i("leyla", String.valueOf(id));
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_CART, KEY_CART_ID+ "=" + id, null) ;

    }
    public void deleteAllCart(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_CART);

    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

       // Log.d(TAG, "Deleted all user info from sqlite");
    }
/*
    public byte[] retreiveImageFromDB() {
        Cursor cur = mDb.query(true, TABLE, new String[]{IMAGE,},
                null, null, null, null,
                IMAGE_ID + " DESC", "1");
        if (cur.moveToFirst()) {
            byte[] blob = cur.getBlob(cur.getColumnIndex(KEY_IMAGE));
            cur.close();
            return blob;
        }
        cur.close();
        return null;
    }

*/
}