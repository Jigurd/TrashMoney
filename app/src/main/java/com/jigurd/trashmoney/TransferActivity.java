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
import android.widget.Toast;

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
            //DEBUG(String.valueOf(balance));

            //string with friends for the dropdown
            String[] friends =  new String[]{"Alice", "Bob", "Bill", "Stephen", "Kurt", "Raymond", "Quentin"};
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
                        balance -= transferAmount;
                        toast("Funds transferred");
                        if (balance < transferAmount)
                        { //check if you can make the same transaction again
                            mPayBtn.setEnabled(false); //button disables automatically if you have
                            // too little money for a repeated payment, makes app a bit more responsive
                        }
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
                        transferAmount = Integer.parseInt(mFieldAmount.getText().toString()) * 100; //multiplied by 100 to compensate for the way we represent currency
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
    @Override
    public void onBackPressed()
    {
        Intent i = new Intent();
        i.putExtra("BALANCE", balance);
        setResult(TransferActivity.RESULT_OK, i);
        finish();
    }

    //sends a toast with parameter message. Laziness function to save me a line of code.
    private void toast(String msg)
    {
        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
