package com.example.q.store.dbstore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.q.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class BaseInfoOperations {

    private static BaseInfoOperations instance;


    public static BaseInfoOperations getInstance() {
        if (instance == null) {
            synchronized (BaseInfoOperations.class) {
                if (instance == null) {
                    instance = new BaseInfoOperations();
                }
            }
        }
        return instance;
    }

    private BaseInfoOperations() {
    }


    public void insertInitialData() {
        insertBaseInfo("赵明", 175F, 72F, "男", "旅游，运动", "计算机", "正常");
        insertBaseInfo("李晓", 173F, 86F, "女", "运动", "软件工程", "偏胖");
        insertBaseInfo("王丽", 163F, 48F, "女", "其他", "物联网", "正常");
    }


    /**
     * 向数据库中插入成员信息
     */
    public long insertBaseInfo(String name, Float height, Float weight, String sex, String hobby, String major, String health) {
        ContentValues values = new ContentValues();
        values.put(InformationContract.BaseInfoEntry.COLUMN_NAME, name);
        values.put(InformationContract.BaseInfoEntry.COLUMN_HEIGHT, height);
        values.put(InformationContract.BaseInfoEntry.COLUMN_WEIGHT, weight);
        values.put(InformationContract.BaseInfoEntry.COLUMN_SEX, sex);
        values.put(InformationContract.BaseInfoEntry.COLUMN_HOBBY, hobby);
        values.put(InformationContract.BaseInfoEntry.COLUMN_MAJOR, major);
        values.put(InformationContract.BaseInfoEntry.COLUMN_HEALTH, health);
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        return db.insert(InformationContract.BaseInfoEntry.TABLE_NAME, null, values);
    }

    /**
     * 更新一个学员的信息
     * @return
     */
    public int updateBaseInfo(Long student_id, String name, Float height, Float weight, String sex, String hobby, String major, String health){
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(InformationContract.BaseInfoEntry.COLUMN_NAME, name);
        values.put(InformationContract.BaseInfoEntry.COLUMN_HEIGHT, height);
        values.put(InformationContract.BaseInfoEntry.COLUMN_WEIGHT, weight);
        values.put(InformationContract.BaseInfoEntry.COLUMN_SEX, sex);
        values.put(InformationContract.BaseInfoEntry.COLUMN_HOBBY, hobby);
        values.put(InformationContract.BaseInfoEntry.COLUMN_MAJOR, major);
        values.put(InformationContract.BaseInfoEntry.COLUMN_HEALTH, health);

        // Define 'where' part of query.
        String selection = InformationContract.BaseInfoEntry.COLUMN_INFO_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(student_id)};
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        return db.update(
                InformationContract.BaseInfoEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


    /**
     * 查询出当前所有的学生
     *
     * @return
     */
    public List<Student> getAllStudent() {
        String[] projection = {
                InformationContract.BaseInfoEntry.COLUMN_INFO_ID,
                InformationContract.BaseInfoEntry.COLUMN_NAME,
                InformationContract.BaseInfoEntry.COLUMN_HEIGHT,
                InformationContract.BaseInfoEntry.COLUMN_WEIGHT,
                InformationContract.BaseInfoEntry.COLUMN_SEX,
                InformationContract.BaseInfoEntry.COLUMN_HOBBY,
                InformationContract.BaseInfoEntry.COLUMN_MAJOR,
                InformationContract.BaseInfoEntry.COLUMN_HEALTH
        };
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        Cursor cursor = db.query(
                InformationContract.BaseInfoEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        ArrayList<Student> students = new ArrayList<Student>();
        while (cursor.moveToNext()) {

            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_INFO_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_NAME));
            Float height = cursor.getFloat(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_HEIGHT));
            Float weight = cursor.getFloat(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_WEIGHT));
            String sex = cursor.getString(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_SEX));
            String hobby = cursor.getString(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_HOBBY));
            String major = cursor.getString(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_MAJOR));
            String health = cursor.getString(cursor.getColumnIndexOrThrow(InformationContract.BaseInfoEntry.COLUMN_HEALTH));
            Student student = new Student(itemId, name, height, weight, sex, hobby, major, health);
            students.add(student);
        }
        cursor.close();
        return students;
    }


    /**
     * 根据学生id删除学生
     * @param student_id
     * @return
     */
    public int deleteStudentById(Long student_id) {
        // Define 'where' part of query.
        String selection = InformationContract.BaseInfoEntry.COLUMN_INFO_ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(student_id)};
        // Issue SQL statement.
        SQLiteDatabase db = MyDbHelper.getInstance().getDb();
        return db.delete(InformationContract.BaseInfoEntry.TABLE_NAME, selection, selectionArgs);
    }


}