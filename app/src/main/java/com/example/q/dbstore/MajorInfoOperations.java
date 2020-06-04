package com.example.q.dbstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.q.bean.Major;

import java.util.ArrayList;

public class MajorInfoOperations {
    private static MajorInfoOperations instance;

    public static MajorInfoOperations getInstance() {
        if (instance == null) {
            synchronized (MajorInfoOperations.class) {
                if (instance == null) {
                    instance = new MajorInfoOperations();
                }
            }
        }
        return instance;
    }

    private MajorInfoOperations() {
    }


    public void insertInitialData() {
        insertMajorInfo("计算机");
        insertMajorInfo("软件工程");
        insertMajorInfo("物联网");
    }


    /**
     * 向数据库中插入专业信息
     */
    public long insertMajorInfo(String major) {
        ContentValues values = new ContentValues();
        values.put(InformationContract.MajorInfoEntry.COLUMN_MAJOR, major);
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        return db.insert(InformationContract.MajorInfoEntry.TABLE_NAME, null, values);
    }

    /**
     * 更新一个专业信息
     * @return
     */
    public int updateMajorInfo(Long major_id, String major){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(InformationContract.MajorInfoEntry.COLUMN_MAJOR, major);
        // Define 'where' part of query.
        String selection = InformationContract.MajorInfoEntry.COLUMN_MAJOR_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(major_id)};
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        return db.update(
                InformationContract.MajorInfoEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    /**
     * 获取数据库中所有major信息
     * @return
     */
    public ArrayList<Major> getAllMajors() {
        String[] projection = {
                InformationContract.MajorInfoEntry.COLUMN_MAJOR_ID,
                InformationContract.MajorInfoEntry.COLUMN_MAJOR
        };
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        Cursor cursor = db.query(
                InformationContract.MajorInfoEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        ArrayList<Major> majors = new ArrayList<Major>();
        while (cursor.moveToNext()) {

            long majorId = cursor.getLong(cursor.getColumnIndexOrThrow(InformationContract.MajorInfoEntry.COLUMN_MAJOR_ID));
            String major = cursor.getString(cursor.getColumnIndexOrThrow(InformationContract.MajorInfoEntry.COLUMN_MAJOR));
            majors.add(new Major(majorId, major));
        }
        cursor.close();
        return majors;
    }


    /**
     * 根据major id删除专业
     * @param major_id
     * @return
     */
    public int deleteMajorById(Long major_id) {
        // Define 'where' part of query.
        String selection = InformationContract.MajorInfoEntry.COLUMN_MAJOR_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(major_id)};
        // Issue SQL statement.
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        return db.delete(InformationContract.MajorInfoEntry.TABLE_NAME, selection, selectionArgs);
    }



}