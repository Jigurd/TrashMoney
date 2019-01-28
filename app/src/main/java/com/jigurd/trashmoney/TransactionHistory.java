package com.jigurd.trashmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class TransactionHistory extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        final Intent i = getIntent();
        final ArrayList<Transaction>  transactionList = (ArrayList<Transaction>) i.getSerializableExtra("LIST");

        ListAdapter adapter = new ListAdapter(this, R.layout.list_helper_view, transactionList);
        final ListView listView = findViewById(R.id.listTransactions);
        listView.setAdapter(adapter);
    }

    //sends a toast with parameter message. Laziness function to save me a line of code.
    private void toast(String msg)
    {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
