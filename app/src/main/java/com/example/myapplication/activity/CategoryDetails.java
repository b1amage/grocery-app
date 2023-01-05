//package com.example.myapplication.activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import com.example.myapplication.R;
//import com.example.myapplication.adapter.CategoryAdapter;
//import com.example.myapplication.adapter.CategoryItemAdapter;
//import com.example.myapplication.adapter.ItemAdapter;
//import com.example.myapplication.components.ActionBar;
//import com.example.myapplication.content.Items;
//import com.example.myapplication.model.Item;
//import com.example.myapplication.utilities.Button;
//import com.example.myapplication.utilities.ColorTransparentUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class CategoryDetails extends AppCompatActivity {
//    private ActionBar detailsActionBar = new ActionBar(R.id.detailsActionBar, this);
//    private ArrayList<Item> itemList = new Items().getItems();
//
//    private LinearLayout addButton;
//    private ListView itemListView;
//
//    private Button cancelButton = new Button(R.id.cancelButton, this);
//    private Button deleteButton = new Button(R.id.deleteButton, this);
//    private RelativeLayout deleteNotification;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_details);
//
//        deleteNotification = findViewById(R.id.deleteNotification);
//        deleteNotification.setBackgroundColor(Color.parseColor(ColorTransparentUtils.transparentColor(R.color.black,70)));
//
//        itemListView = findViewById(R.id.categoryItemList);
//        String title = getIntent().getStringExtra("actionBarTitle");
//        detailsActionBar.createActionBar(title, R.drawable.ic_back, R.drawable.navbutton_shape);
//
//        cancelButton.createInactiveButton("Cancel", onClickCancelButton());
//        deleteButton.createActiveButton("Yes, delete", onClickDeleteButton());
//
//        addButton = findViewById(R.id.addButton);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(CategoryDetails.this, CreateItemForm.class);
//                startActivity(intent);
//            }
//        });
//        CategoryItemAdapter itemAdapter = new CategoryItemAdapter(this, itemList);
//        itemListView.setAdapter(itemAdapter);
//    }
//
//    private View.OnClickListener onClickCancelButton(){
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteNotification.setVisibility(View.INVISIBLE);
//                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_LONG).show();
//            }
//        };
//    }
//
//    private View.OnClickListener onClickDeleteButton(){
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_LONG).show();
//            }
//        };
//    }
//}