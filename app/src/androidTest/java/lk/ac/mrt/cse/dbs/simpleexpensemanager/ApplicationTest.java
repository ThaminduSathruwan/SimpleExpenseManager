/*
 * Copyright 2015 Department of Computer Science and Engineering, University of Moratuwa.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *                  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package lk.ac.mrt.cse.dbs.simpleexpensemanager;

//import android.app.Application;
//import android.test.ApplicationTestCase;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.PersistentExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType.INCOME;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

//public class ApplicationTest extends ApplicationTestCase<Application> {
//    public ApplicationTest() {
//        super(Application.class);
//    }
//}

public class ApplicationTest {
    private static ExpenseManager expenseManager;

    @Before
    public void setUp(){
        Context context = ApplicationProvider.getApplicationContext();
        expenseManager = new PersistentExpenseManager(context);
    }

    @Test
    public void testAddAccount(){
        expenseManager.addAccount("123","ABC","AaBbCc",200.0);
        List<String> accountNumbers = expenseManager.getAccountNumbersList();
        assertTrue(accountNumbers.contains("123"));
    }

    @Test
    public void testTransaction() throws InvalidAccountException {
        expenseManager.addAccount("456","DEF","DdEeFf",1000.0);
        expenseManager.updateAccountBalance("456",25,05,2024, ExpenseType.INCOME, "500");
        AccountDAO accountDAO = expenseManager.getAccountsDAO();
        assertEquals((accountDAO.getAccount("456")).getBalance(),Double.parseDouble("1500"),0.01);
    }
}