package com.unitec.bloodbank.bloodbankproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String address;
    private String name;
    private String blood;
    private double lat;
    private double lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        address = intent.getStringExtra(DetailActivity.USER_ADDRESS);
        name = intent.getStringExtra(DetailActivity.USER_NAME);
        blood = intent.getStringExtra(DetailActivity.USER_BLOOD);

        getLocationFromAddress(address);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in donor location and move the camera
        LatLng donorLoc = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(donorLoc).title(blood + " Donor: " + name).snippet("Address: " + address)).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(donorLoc, 14));
    }

    // Registering transition when device back button pressed
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public void getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress,2);
            if (address != null) {
                Address location = address.get(0);
                lat = location.getLatitude();
                lng = location.getLongitude();
            }
        }
        catch (IOException e){

        }
    }
}
