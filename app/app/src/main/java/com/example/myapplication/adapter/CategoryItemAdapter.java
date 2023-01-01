package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.example.myapplication.activity.CategoryDetails;
import com.example.myapplication.activity.Dashboard;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.ColorTransparentUtils;

import java.util.ArrayList;

public class CategoryItemAdapter extends ArrayAdapter<Item> {

    private ImageView edit;
    private LinearLayout deleteLayout;
    private RelativeLayout deleteNotification;

    public CategoryItemAdapter(@NonNull Context context, ArrayList<Item> categoryItems) {
        super(context, 0, categoryItems);
        deleteNotification = ((Activity) context).findViewById(R.id.deleteNotification);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);
        }

        Item item = (Item) getItem(position);

        ((ImageView) listItemView.findViewById(R.id.itemImage)).setImageResource(item.getImg());
        ((TextView) listItemView.findViewById(R.id.itemName)).setText(item.getName());
        ((TextView) listItemView.findViewById(R.id.itemInfo)).setText(item.getCategory());
        ((TextView) listItemView.findViewById(R.id.itemPrice)).setText("$" + String.valueOf(item.getPrice()));
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
