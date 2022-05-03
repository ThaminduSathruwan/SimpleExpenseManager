package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DataBaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;

public class PersistentExpenseManager extends ExpenseManager{
    Context context;

    public PersistentExpenseManager(Context context) {
        this.context = context;
        setup();
    }

    @Override
    public void setup() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        TransactionDAO persistentTransactionDAO = new PersistentTransactionDAO(dataBaseHelper);
        setTransactionsDAO(persistentTransactionDAO);
        AccountDAO persistentAccountDAO = new PersistentAccountDAO(dataBaseHelper);
        setAccountsDAO(persistentAccountDAO);
    }
}
