package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
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
import com.example.myapplication.activity.StoreForm;
import com.example.myapplication.activity.VoucherForm;
import com.example.myapplication.model.Location;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Location> {

    private ImageView edit;
    private LinearLayout deleteLayout;
    private RelativeLayout deleteNotification;
    private LinearLayout mask;

    public LocationAdapter(@NonNull Context context, ArrayList<Location> locations) {
        super(context, 0, locations);
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

        Location location = (Location) getItem(position);

        ((ImageView) listItemView.findViewById(R.id.itemImage)).setImageResource(R.drawable.shop);
        ((TextView) listItemView.findViewById(R.id.itemName)).setText(location.getAddress());
        ((TextView) listItemView.findViewById(R.id.itemName)).setElegantTextHeight(true);
        ((TextView) listItemView.findViewById(R.id.itemName)).setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        ((TextView) listItemView.findViewById(R.id.itemName)).setSingleLine(false);


        ((TextView) listItemView.findViewById(R.id.itemInfo)).setVisibility(View.GONE);
        ((TextView) listItemView.findViewById(R.id.itemPrice)).setVisibility(View.GONE);

        edit = listItemView.findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StoreForm.class);
                intent.putExtra("location", location);
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


