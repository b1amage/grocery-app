package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.utilities.Button;
import com.example.myapplication.utilities.Text;

public class Preview extends AppCompatActivity {
    private Button activeButton = new Button(R.id.active_button, this);
    private Button inactiveButton = new Button(R.id.inactive_button, this);
    private Button navButton = new Button(R.id.navbar_button, this);
    private Button iconButton = new Button(R.id.icon_button, this);

    private Text title = new Text(R.id.title, this);
    private Text text = new Text(R.id.text, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        activeButton.createActiveButton("Diablo", activeButtonClick());

        inactiveButton.createInactiveButton("Zeref", inactiveButtonClick());
        navButton.createNavbarButton(R.drawable.ic_back, "Back", navButtonClick());
        iconButton.createButton(R.drawable.ic_back, buttonClick());

        title.createTitle(32, "Title", R.color.primary_100);
        text.createText(16, "Text", R.color.tertiary_gray);
    }

    // onClick function for active button
    private View.OnClickListener activeButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Preview.this, "Active button", Toast.LENGTH_LONG).show();
            }
        };
    }

    // onClick function for inactive button
    private View.OnClickListener inactiveButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Preview.this, "Inactive button", Toast.LENGTH_LONG).show();
            }
        };
    }

    // onClick function for navigation button
    private View.OnClickListener navButtonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Preview.this, "Nav button", Toast.LENGTH_LONG).show();
            }
        };
    }

    // onClick function for button
    private View.OnClickListener buttonClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Preview.this, "Button", Toast.LENGTH_LONG).show();
            }
        };
    }
}
