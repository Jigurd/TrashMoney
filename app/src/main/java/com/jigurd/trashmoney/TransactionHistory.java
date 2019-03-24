package com.jigurd.trashmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id)
            {
                Transaction element = transactionList.get(pos);
                Toast.makeText(getApplicationContext(),
                element.getRecipient()+" "+element.displayAmount(),
                Toast.LENGTH_LONG).show();
                return true;
            }
        });
        listView.setAdapter(adapter);
    }
}
