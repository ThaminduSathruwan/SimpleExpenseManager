package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {

    DataBaseHelper dataBaseHelper;

    public PersistentAccountDAO(DataBaseHelper dataBaseHelper){
        this.dataBaseHelper = dataBaseHelper;
    }

    @Override
    public List<String> getAccountNumbersList() {
        List<String> returnList = new ArrayList<>();

        String queryString = "SELECT " + DataBaseHelper.COLUMN_ACCOUNT_NO + " FROM " + DataBaseHelper.ACCOUNT_TABLE;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String accountNo = cursor.getString(0);
                returnList.add(accountNo);

            }while(cursor.moveToNext());
        }
        else{
        }

        cursor.close();
        db.close();


        return returnList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + DataBaseHelper.ACCOUNT_TABLE;
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                String accountNo = cursor.getString(1);
                String bankName = cursor.getString(2);
                String accountHolderName = cursor.getString(3);
                double balance = cursor.getDouble(4);

                Account newAccount = new Account(accountNo, bankName, accountHolderName, balance);
                returnList.add(newAccount);

            }while(cursor.moveToNext());
        }
        else{
        }

        cursor.close();
        db.close();
        return returnList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Account returnAccount;
        String queryString = "SELECT * FROM " + DataBaseHelper.ACCOUNT_TABLE + " WHERE " + DataBaseHelper.COLUMN_ACCOUNT_NO + " = " + "? ";
        String[] selectionArgs = {accountNo};
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, selectionArgs);

        if(cursor.moveToFirst()){
            String bankName = cursor.getString(2);
            String accountHolderName = cursor.getString(3);
            double balance = cursor.getDouble(4);

            returnAccount = new Account(accountNo, bankName, accountHolderName, balance);

        }
        else{
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }

        cursor.close();
        db.close();
        return returnAccount;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DataBaseHelper.COLUMN_ACCOUNT_NO, account.getAccountNo());
        cv.put(DataBaseHelper.COLUMN_BANK_NAME, account.getBankName());
        cv.put(DataBaseHelper.COLUMN_ACCOUNT_HOLDER_NAME, account.getAccountHolderName());
        cv.put(DataBaseHelper.COLUMN_BALANCE, account.getBalance());

        long insert = db.insert(DataBaseHelper.ACCOUNT_TABLE, null, cv);
        db.close();
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String queryString = "DELETE FROM " + DataBaseHelper.ACCOUNT_TABLE + " WHERE " +  DataBaseHelper.COLUMN_ACCOUNT_NO + " = " + "? ";
        String[] selectionArgs = {accountNo};

        Cursor cursor = db.rawQuery(queryString, selectionArgs);
        cursor.close();
        db.close();
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        String queryString = "SELECT * FROM " + DataBaseHelper.ACCOUNT_TABLE + " WHERE " + DataBaseHelper.COLUMN_ACCOUNT_NO + " = " + "? ";
        String[] selectionArgs = {accountNo};
        Cursor cursor = db.rawQuery(queryString, selectionArgs);

        if(cursor.moveToFirst()){
            switch (expenseType) {
                case EXPENSE:
                    db.execSQL(("UPDATE " + DataBaseHelper.ACCOUNT_TABLE + " SET " + DataBaseHelper.COLUMN_BALANCE + " = " +(cursor.getDouble(4) - amount) + " WHERE " + DataBaseHelper.COLUMN_ACCOUNT_NO + " = " + "? "),selectionArgs);
                    break;
                case INCOME:
                    db.execSQL(("UPDATE " + DataBaseHelper.ACCOUNT_TABLE + " SET " + DataBaseHelper.COLUMN_BALANCE + " = " +(cursor.getDouble(4) + amount) + " WHERE " + DataBaseHelper.COLUMN_ACCOUNT_NO + " = " + "? "),selectionArgs);
                    break;
            }
        }
        else{
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        cursor.close();
        db.close();
    }
}
