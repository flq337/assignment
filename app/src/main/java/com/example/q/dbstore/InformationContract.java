package com.example.q.dbstore;


public class InformationContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private InformationContract() {
    }

    /* Inner class that defines the table contents */
    public static class BaseInfoEntry {
        public static final String TABLE_NAME = "base_info";
        public static final String COLUMN_INFO_ID = "infoid";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_HEIGHT = "height";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_HOBBY = "hobby";
        public static final String COLUMN_MAJOR = "major";
        public static final String COLUMN_HEALTH = "health";
    }

    public static class MajorInfoEntry {
        public static final String TABLE_NAME = "major_info";
        public static final String COLUMN_MAJOR_ID = "majorid";
        public static final String COLUMN_MAJOR = "major";
    }

}
