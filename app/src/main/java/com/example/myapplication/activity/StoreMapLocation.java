package com.example.myapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.components.ActionBar;
import com.example.myapplication.content.Locations;
import com.example.myapplication.databinding.ActivityStoreMapLocationBinding;
import com.example.myapplication.model.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class StoreMapLocation extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityStoreMapLocationBinding binding;
    private ActionBar actionBar = new ActionBar(R.id.storeInformationActionBar, this);
    private Location location;
    private ArrayList<Location> locations = new Locations().getLocations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStoreMapLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        actionBar.createActionBar("Store Information", R.drawable.ic_back, R.drawable.navbutton_shape);

        location = (Location) getIntent().getSerializableExtra("location");

        TextView storeAddress = findViewById(R.id.storeAddress);

        storeAddress.setText(location.getAddress());
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng storeLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        LatLng storeLocation = new LatLng(10.82302, 106.62965);
        mMap.addMarker(new MarkerOptions().position(storeLocation).title(location.getAddress()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }
}