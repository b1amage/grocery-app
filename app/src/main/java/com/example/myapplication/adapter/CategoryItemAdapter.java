package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.activity.CreateItemForm;
import com.example.myapplication.activity.Dashboard;
import com.example.myapplication.activity.SignInActivity;
import com.example.myapplication.activity.VoucherManagement;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Item;
import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemAdapter extends BaseAdapter {

    private ImageView edit;
    private LinearLayout deleteLayout;
    private RelativeLayout deleteNotification;
    private LinearLayout mask;
    private List<Item> items;

    public CategoryItemAdapter(@NonNull Context context, ArrayList<Item> categoryItems) {
        deleteNotification = ((Activity) context).findViewById(R.id.deleteNotification);
        mask = ((Activity) context).findViewById(R.id.ll_mask);
        items = categoryItems;
    }


    public void updateResults(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        }

        Item item = (Item) getItem(position);

//        ((ImageView) listItemView.findViewById(R.id.itemImage)).setImageResource(item.getImg());
        ImageLoader.loadImg(item.getImageURL(), listItemView.findViewById(R.id.itemImage));
        ((TextView) listItemView.findViewById(R.id.itemName)).setText(item.getName());
        ((TextView) listItemView.findViewById(R.id.itemInfo)).setText(item.getCategory());
        ((TextView) listItemView.findViewById(R.id.itemPrice)).setText("VND" + String.valueOf(item.getPrice()));

        mask.setVisibility(View.INVISIBLE);

        edit = listItemView.findViewById(R.id.editButton);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), CreateItemForm.class);
                intent.putExtra("item", item);
                intent.putExtra("title", "Update item");
                Toast.makeText(parent.getContext(), "Edit", Toast.LENGTH_LONG).show();
                parent.getContext().startActivity(intent);
            }
        });

        deleteLayout = listItemView.findViewById(R.id.delete);
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNotification.setVisibility(View.VISIBLE);
                mask.setVisibility(View.VISIBLE);

                LinearLayout deleteButton = deleteNotification.findViewById(R.id.deleteButton);

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getContext(), voucher.toString(), Toast.LENGTH_LONG).show();
                        (new APIHandler(parent.getContext())).deleteRequest("/item/delete", item.get_id(), new VolleyResponseListener() {
                            @Override
                            public void onError(String message, int statusCode) {
                                System.err.println(message);
                                parent.getContext().startActivity(new Intent(parent.getContext(), SignInActivity.class));
                            }

                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                System.out.println(response);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        parent.getContext().startActivity(new Intent(parent.getContext(), Dashboard.class));
                                    }
                                }, 2000);
                            }

                        });
                    }
                });
            }
        });

        return listItemView;
    }

}
