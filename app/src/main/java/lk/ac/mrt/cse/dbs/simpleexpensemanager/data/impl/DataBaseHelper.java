package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String ACCOUNT_TABLE = "ACCOUNT_TABLE";
    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String COLUMN_ACCOUNT_ID = "ACCOUNT_ID";
    public static final String COLUMN_TRANSACTION_ID = "TRANSACTION_ID";
    public static final String COLUMN_ACCOUNT_NO = "ACCOUNT_NO";
    public static final String COLUMN_TRANSACTION_DATE = "TRANSACTION_DATE";
    public static final String COLUMN_BANK_NAME = "BANK_NAME";
    public static final String COLUMN_ACCOUNT_HOLDER_NAME = "ACCOUNT_HOLDER_NAME";
    public static final String COLUMN_EXPENSE_TYPE = "EXPENSE_TYPE";
    public static final String COLUMN_BALANCE = "BALANCE";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String EXPENSE_MANAGER_DB = "190257C.db";
    public static final int VERSION = 1;

    public DataBaseHelper(@Nullable Context context) {
        super(context, EXPENSE_MANAGER_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement1 = "CREATE TABLE " + ACCOUNT_TABLE + " (" +
                COLUMN_ACCOUNT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ACCOUNT_NO + " TEXT, " + COLUMN_BANK_NAME + " TEXT, " +
                COLUMN_ACCOUNT_HOLDER_NAME + " TEXT, " +
                COLUMN_BALANCE + " REAL)";

        sqLiteDatabase.execSQL(createTableStatement1);

        String createTableStatement2 = "CREATE TABLE " + TRANSACTION_TABLE + "  (" +
                COLUMN_TRANSACTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TRANSACTION_DATE + " DATE, " +
                COLUMN_ACCOUNT_NO + " TEXT, " +
                COLUMN_EXPENSE_TYPE + " INTEGER, " +
                COLUMN_AMOUNT + " REAL)";

        sqLiteDatabase.execSQL(createTableStatement2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRANSACTION_TABLE);
        onCreate(sqLiteDatabase);
    }
}
