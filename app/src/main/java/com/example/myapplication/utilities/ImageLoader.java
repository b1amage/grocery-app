package com.example.myapplication.utilities;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {
    private ImageLoader() {}

    public static void loadImg(String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .into(imageView);
    }
}
