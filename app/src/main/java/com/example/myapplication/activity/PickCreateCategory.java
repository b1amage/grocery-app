package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.utilities.Text;

public class PickCreateCategory extends AppCompatActivity {

    private ActionBar pickCreateCategoryActionBar = new ActionBar(R.id.pickCreateCategoryActionBar, this);
    private Text pageIntro = new Text(R.id.pageIntroduction, this);
//    private Button navButton = new Button(R.id.navbar_button, this);
    private Button itemsButton;
    private Button vouchersButton;
    private Button locationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_create_category);
        pickCreateCategoryActionBar.createActionBar("Create", R.drawable.ic_back, R.drawable.navbutton_shape);

        itemsButton = findViewById(R.id.createItems);
        itemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateItemForm.class);
                startActivity(intent);
            }
        });

        vouchersButton = findViewById(R.id.createVouchers);
        vouchersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), VoucherForm.class);
                startActivity(intent);
            }
        });

        locationsButton = findViewById(R.id.createLocations);
        locationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StoreForm.class);
                startActivity(intent);
            }
        });
        pageIntro.createTitle(40,"Please select the topic you want ot create", R.color.black, Gravity.START);
    }

}