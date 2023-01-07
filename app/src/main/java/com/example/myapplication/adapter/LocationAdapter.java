package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
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
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Location;
import com.example.myapplication.model.Voucher;
import com.example.myapplication.utilities.Button;

import java.util.ArrayList;

public class LocationAdapter extends ArrayAdapter<Location> {

    private ImageView edit;
    private LinearLayout deleteLayout;
    private RelativeLayout deleteNotification;

    public LocationAdapter(@NonNull Context context, ArrayList<Location> locations) {
        super(context, 0, locations);
        deleteNotification = ((Activity) context).findViewById(R.id.deleteNotification);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_card, parent, false);
        }

        Location location = (Location) getItem(position);

        ((ImageView) listItemView.findViewById(R.id.itemImage)).setImageResource(R.drawable.shop);
        ((TextView) listItemView.findViewById(R.id.itemName)).setText(location.getAddress());

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
                Toast.makeText(getContext(), "Edit", Toast.LENGTH_LONG).show();
            }
        });

        deleteLayout = listItemView.findViewById(R.id.delete);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotification.setVisibility(View.VISIBLE);
            }
        });

        return listItemView;
    }

}


