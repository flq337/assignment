package com.example.q.dbstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.q.MyApplication;

public class MyDbHelper extends SQLiteOpenHelper {

    private static MyDbHelper instance = null;
    private SQLiteDatabase writableDatabase;

    private MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        writableDatabase = getWritableDatabase();
    }

    public static synchronized MyDbHelper getInstance() {
        if (instance == null) {
            synchronized (MyDbHelper.class) {
                if (instance == null) {
                    instance = new MyDbHelper(MyApplication.getContext());
                }
            }
        }
        return instance;
    }


    public SQLiteDatabase getDb(){
        return writableDatabase;
    }

    private static final String TAG = "databaseOP";
    private final static String DATABASE_NAME = "information.db";
    private final static int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_BASE_INFO_ENTRIES =
            "CREATE TABLE " + InformationContract.BaseInfoEntry.TABLE_NAME + " (" +
                    InformationContract.BaseInfoEntry.COLUMN_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    InformationContract.BaseInfoEntry.COLUMN_NAME + " TEXT NOT NULL," +
                    InformationContract.BaseInfoEntry.COLUMN_HEIGHT + " FLOAT ," +
                    InformationContract.BaseInfoEntry.COLUMN_WEIGHT + " FLOAT ," +
                    InformationContract.BaseInfoEntry.COLUMN_SEX + " TEXT ," +
                    InformationContract.BaseInfoEntry.COLUMN_HOBBY + " TEXT ," +
                    InformationContract.BaseInfoEntry.COLUMN_MAJOR + " TEXT ," +
                    InformationContract.BaseInfoEntry.COLUMN_HEALTH + " TEXT)";


    private static final String SQL_DELETE_BASE_INFO_ENTRIES =
            "DROP TABLE IF EXISTS " + InformationContract.BaseInfoEntry.TABLE_NAME;

    private static final String SQL_CREATE_MAJOR_INFO_ENTRIES =
            "CREATE TABLE " + InformationContract.MajorInfoEntry.TABLE_NAME + " (" +
                    InformationContract.MajorInfoEntry.COLUMN_MAJOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    InformationContract.MajorInfoEntry.COLUMN_MAJOR + " TEXT NOT NULL)";

    private static final String SQL_DELETE_MAJOR_INFO_ENTRIES =
            "DROP TABLE IF EXISTS " + InformationContract.MajorInfoEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BASE_INFO_ENTRIES);
        db.execSQL(SQL_CREATE_MAJOR_INFO_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_BASE_INFO_ENTRIES);
        db.execSQL(SQL_DELETE_MAJOR_INFO_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
