package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(@NonNull Context context, ArrayList<Category> categories) {
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.category, parent, false);
        }

        Category categoryModel = (Category) getItem(position);
        ImageView categoryIcon = listItemView.findViewById(R.id.categoryIcon);
        TextView categoryTitle = listItemView.findViewById(R.id.categoryTitle);
        categoryTitle.setText(categoryModel.getCategoryName());
        categoryIcon.setImageResource(categoryModel.getIconId());
        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), categoryTitle.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        return listItemView;
    }

}
