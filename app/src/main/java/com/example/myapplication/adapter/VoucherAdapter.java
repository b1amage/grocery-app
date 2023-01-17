package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.activity.CreateItemForm;
import com.example.myapplication.activity.VoucherForm;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;

import java.util.ArrayList;

public class VoucherAdapter extends ArrayAdapter<Voucher> {

    private ImageView edit;
    private LinearLayout deleteLayout;
    private RelativeLayout deleteNotification;
    private LinearLayout mask;

    public VoucherAdapter(@NonNull Context context, ArrayList<Voucher> vouchers) {
        super(context, 0, vouchers);
        deleteNotification = ((Activity) context).findViewById(R.id.deleteNotification);
        mask = ((Activity) context).findViewById(R.id.ll_mask);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_card, parent, false);
        }

        mask.setVisibility(View.INVISIBLE);

        Voucher voucher = (Voucher) getItem(position);

        ((ImageView) listItemView.findViewById(R.id.itemImage)).setImageResource(R.drawable.voucher);
        ((TextView) listItemView.findViewById(R.id.itemName)).setText(voucher.getCode());
        ((TextView) listItemView.findViewById(R.id.itemInfo)).setText(voucher.getDescription());
        ((TextView) listItemView.findViewById(R.id.itemPrice)).setText(voucher.getType().equals("percentage") ? "$" + voucher.getValue() : voucher.getValue() + "VND");
//        listItemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(getContext(), categoryTitle.getText().toString(), Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getContext(), CategoryDetails.class);
//                intent.putExtra("actionBarTitle", categoryTitle.getText().toString());
//                getContext().startActivity(intent);
//            }
//        });

        edit = listItemView.findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), VoucherForm.class);
                intent.putExtra("voucher", voucher);
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_LONG).show();
                getContext().startActivity(intent);
            }
        });

        deleteLayout = listItemView.findViewById(R.id.delete);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mask.setVisibility(View.VISIBLE);
                deleteNotification.setVisibility(View.VISIBLE);
            }
        });

        return listItemView;
    }

}

