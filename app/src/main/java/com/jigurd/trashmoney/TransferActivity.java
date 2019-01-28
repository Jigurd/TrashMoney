package com.jigurd.trashmoney;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity
{
    private int balance = 0; //account balance
    private int transferAmount=0; //amount to be transferred in transactions

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_transfer);

            final Intent i = getIntent();
            balance = i.getIntExtra("BALANCE", 0); //get balance from main activity

            //string with friends for the dropdown
            String[] friends;
            try
            {
            friends = i.getStringArrayExtra("NAMES");
            }catch(NullPointerException e)
            {
                friends = new String[]{"none"};
                setResult(1);
                finish();
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, friends);

            //define dropdown using friends
            final Spinner dropdown = findViewById(R.id.spinner1);
            dropdown.setAdapter(adapter);

            //define button, disable it
            final Button mPayBtn = findViewById(R.id.btn_transactions);
            mPayBtn.setEnabled(false);
            //define lbl_amount_check
            final TextView mAmountCheck = findViewById(R.id.lbl_amount_check);

            mPayBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //get transfer amount from field

                    if (balance > transferAmount)
                    { //if user can afford it
                        balance -= transferAmount;  //remove amount from balance

                        //create new transaction object
                        Transaction newTransaction = new Transaction(dropdown.getSelectedItem().toString(),transferAmount,balance);

                        //return to MainActivity with our new data
                        Intent i = new Intent();
                        i.putExtra("NEW", newTransaction);
                        i.putExtra("BALANCE", balance);
                        setResult(TransferActivity.RESULT_OK, i);
                        finish();
                    }
                }
            });


            //Make the text field determine if the amount transferred is valid, and enables button if it is
            final EditText mFieldAmount = findViewById(R.id.field_amount);
            mFieldAmount.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    try
                    {
                        //float is briefly needed here to allow for decimal currency transfers.
                        //this might introduce floating point errors where occasionally
                        // //a euro is dropped. I am not sure how to fix this.
                        Float temp = Float.parseFloat(mFieldAmount.getText().toString());
                        temp = temp*100; //multiplied by 100 to compensate for the way we represent currency
                        transferAmount= temp.intValue();

                    } catch(NumberFormatException e)
                    {
                        transferAmount = 0;
                    }

                    //DEBUG(String.valueOf(transferAmount));

                    if (transferAmount > 0 && transferAmount < balance)
                    {
                        mPayBtn.setEnabled(true);
                        mAmountCheck.setText(R.string.lbl_empty);
                    } else {
                        mPayBtn.setEnabled(false);
                        mAmountCheck.setText(R.string.lbl_amount_check);
                    }
                }
                @Override
                public void afterTextChanged(Editable s) {}
            });
    }
}
