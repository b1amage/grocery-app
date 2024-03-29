package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context) {
        this.context = context;
        this.categoryList = new ArrayList<>(
                Arrays.asList(
                        new Category(1, "all", R.drawable.shop),
                        new Category(2, "vegetable", R.drawable.vegetable),
                        new Category(3, "meat", R.drawable.meat),
                        new Category(4, "fruit", R.drawable.fruit),
                        new Category(5, "diary", R.drawable.diary),
                        new Category(6, "canned", R.drawable.canned),
                        new Category(7, "snack", R.drawable.snack),
                        new Category(8, "drink", R.drawable.drink),
                        new Category(9, "spice", R.drawable.spice),
                        new Category(10, "household", R.drawable.household),
                        new Category(11, "personal hygiene", R.drawable.hygine)
                )
        );
    }

    public CategoryAdapter(Context context, boolean forCreateForm) {
        this.context = context;

        if (forCreateForm) {
            this.categoryList = new ArrayList<>(
                    Arrays.asList(
                            new Category(1, "vegetable", R.drawable.vegetable),
                            new Category(2, "meat", R.drawable.meat),
                            new Category(3, "fruit", R.drawable.fruit),
                            new Category(4, "diary", R.drawable.diary),
                            new Category(5, "canned", R.drawable.canned),
                            new Category(6, "snack", R.drawable.snack),
                            new Category(7, "drink", R.drawable.drink),
                            new Category(8, "spice", R.drawable.spice),
                            new Category(9, "household", R.drawable.household),
                            new Category(10, "personal hygiene", R.drawable.hygine)
                    )
            );
        }
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.category_select, viewGroup, false);


        ImageView img = rootView.findViewById(R.id.category_select_img);
        TextView text = rootView.findViewById(R.id.category_select_name);

        img.setImageResource(categoryList.get(i).getImg());
        text.setText(categoryList.get(i).getCategoryName());

        return rootView;
    }
}
