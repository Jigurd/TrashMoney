package com.jigurd.trashmoney;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<Transaction>
{

    private LayoutInflater mInflater;
    private ArrayList<Transaction> users;
    private int mViewResourceId;

    ListAdapter(Context context, int textViewResourceId, ArrayList<Transaction> users)
    {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = mInflater.inflate(mViewResourceId, null);

        Transaction transaction = users.get(position);

        if (transaction != null)
        {
            TextView Amount = convertView.findViewById(R.id.txtAmount);
            TextView BalanceAfter = convertView.findViewById(R.id.txtBalanceAfter);
            TextView Name = convertView.findViewById(R.id.txtName);
            TextView Time = convertView.findViewById(R.id.txtTime);
            if (Amount != null)
            {
                Amount.setText(transaction.displayAmount());
            }
            if (BalanceAfter != null)
            {
                BalanceAfter.setText(transaction.displayBalanceAfter());
            }
            if (Name != null)
            {
                Name.setText(transaction.getRecipient());
            }
            if (Time != null)
            {
                Time.setText(transaction.getTransferTime());
            }
        }

        return convertView;
    }
}
