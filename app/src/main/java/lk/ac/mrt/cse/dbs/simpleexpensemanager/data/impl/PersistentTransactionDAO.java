package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistentTransactionDAO implements TransactionDAO {

    DataBaseHelper dataBaseHelper;

    public PersistentTransactionDAO(DataBaseHelper dataBaseHelper) {
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateStr = sdf.format(date);
        String queryString = "INSERT INTO " + DataBaseHelper.TRANSACTION_TABLE + " ( " +
                DataBaseHelper.COLUMN_TRANSACTION_DATE + ", " +
                DataBaseHelper.COLUMN_ACCOUNT_NO + ", " +
                DataBaseHelper.COLUMN_EXPENSE_TYPE + ", " +
                DataBaseHelper.COLUMN_AMOUNT +
                ")  VALUES (" + "? , " + "?, " + expenseType.ordinal() + ", " + amount + ") ";
        String[] selectionArgs = {dateStr, accountNo};
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        db.execSQL(queryString,selectionArgs);
        db.close();
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        List<Transaction> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + DataBaseHelper.TRANSACTION_TABLE;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                Date transactionDate;
                try{
                    transactionDate = new SimpleDateFormat("dd-MM-yyyy").parse(cursor.getString(1));
                }
                catch (ParseException e){
                    return null;
                }
                String accountNo = cursor.getString(2);
                ExpenseType expenseType = cursor.getInt(3) == 0? ExpenseType.EXPENSE: ExpenseType.INCOME;
                double amount = cursor.getDouble(4);

                Transaction newTransaction = new Transaction(transactionDate, accountNo, expenseType, amount);
                returnList.add(newTransaction);

            }while(cursor.moveToNext());
        }
        else{
        }

        cursor.close();
        db.close();

        return returnList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + DataBaseHelper.TRANSACTION_TABLE + " ORDER BY " + DataBaseHelper.COLUMN_TRANSACTION_ID + " DESC " + " LIMIT " + limit ;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                Date transactionDate;
                try{
                    transactionDate = new SimpleDateFormat("dd-MM-yyyy").parse(cursor.getString(1));
                }
                catch (ParseException e){
                    return null;
                }
                String accountNo = cursor.getString(2);
                ExpenseType expenseType = cursor.getInt(3) == 0? ExpenseType.EXPENSE: ExpenseType.INCOME;
                double amount = cursor.getDouble(4);

                Transaction newTransaction = new Transaction(transactionDate, accountNo, expenseType, amount);
                returnList.add(newTransaction);

            }while(cursor.moveToNext());
        }
        else{
        }

        cursor.close();
        db.close();

        return returnList;
    }
}
