package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.example.myapplication.activity.LocationManagement;
import com.example.myapplication.activity.SignInActivity;
import com.example.myapplication.activity.StoreForm;
import com.example.myapplication.activity.VoucherForm;
import com.example.myapplication.api.APIHandler;
import com.example.myapplication.api.VolleyResponseListener;
import com.example.myapplication.model.Location;

import org.json.JSONException;
import org.json.JSONObject;

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
                intent.putExtra("title", "Update location");
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

                LinearLayout deleteButton = deleteNotification.findViewById(R.id.deleteButton);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getContext(), voucher.toString(), Toast.LENGTH_LONG).show();
                        (new APIHandler(getContext())).deleteRequest("/location/delete", location.get_id(), new VolleyResponseListener() {
                            @Override
                            public void onError(String message, int statusCode) {
                                System.err.println(message);
                                getContext().startActivity(new Intent(getContext(), SignInActivity.class));
                            }

                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                System.out.println(response);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        getContext().startActivity(new Intent(getContext(), LocationManagement.class));
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


