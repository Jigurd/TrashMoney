package com.jigurd.trashmoney;


import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    //CONSTS
    final static int MIN_BALANCE = 9000;
    final static int MAX_BALANCE = 11000;
    final static int TRANSFER_REQUEST_ID = 1;

    private int balance = 0;
    private String euroString = "00.00";
    private TextView balanceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //generate buttons
        final Button mTransferButton = findViewById(R.id.btn_transfer);
        final Button mTransactionsButton = findViewById(R.id.btn_transactions);

        //generate currency value
        final Random rand = new Random(System.currentTimeMillis());
        balance = rand.nextInt(MAX_BALANCE-MIN_BALANCE+1)+MIN_BALANCE;
        //currency kept as an int at 100x its actual value,
        //to prevent floating point errors

        //textView for the balance is set to the random value
        balanceView = findViewById(R.id.lbl_balance);
        updateBalanceView();

        ////DEBUG
        //final Button mDebugButton = findViewById(R.id.btn_debug);
        //mDebugButton.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        DEBUG(String.valueOf(balance));
        //        updateBalanceView();
        //    }
        //});


        //add functionality to transfer button
        mTransferButton.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View v)
            {
                Intent i = new Intent (MainActivity.this, TransferActivity.class);
                i.putExtra("BALANCE", balance);
                startActivityForResult(i, TRANSFER_REQUEST_ID);
            }
        });

        //add functionality to history button
        mTransactionsButton.setOnClickListener(new View.OnClickListener()
        {@Override
        public void onClick(View v)
        {
            Intent i = new Intent (MainActivity.this, TransactionHistory.class);
            startActivity(i);
        }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode)
        {
            case (TRANSFER_REQUEST_ID):
            {
                if (resultCode== Activity.RESULT_OK)
                {
                    balance = data.getIntExtra("BALANCE", balance);
                    updateBalanceView();
                }
            }
        }
    }

    //updates display of balance according to current value of "balance"
    private void updateBalanceView()
    {
        //divide balance value by 100 as a float, then display with two decimal precision. The actual
        //currency value is not changed, so division inaccuracy should not affect the currency.
        euroString = String.format("%.2f",(float)balance/100.0);
        balanceView.setText(euroString);

        //android studio reports an error here, but according to the documentation this is only
        //relevant for machine read input, which this is not, so it's fine.
    }

    private void DEBUG(String msg)
    {
        Toast debug = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        debug.show();
    }
}
