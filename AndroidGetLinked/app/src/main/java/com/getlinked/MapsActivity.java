package com.getlinked;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    String lat = "", lng = "", category_id = "";
    LatLng latlng = null;
    ArrayList<String> pNames, pLat, pLng, pEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        lat = getIntent().getStringExtra("lat");
        lng = getIntent().getStringExtra("lng");
        category_id = getIntent().getStringExtra("category_id");
        pNames = getIntent().getStringArrayListExtra("pNames");
        pEmail = getIntent().getStringArrayListExtra("pEmail");
        pLat = getIntent().getStringArrayListExtra("pLat");
        pLng = getIntent().getStringArrayListExtra("pLng");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        mMap.setMinZoomPreference(10f);
        //add markers for 5 nearest professionals
        if(pLat != null)  {
            for (int i = 0; i < pLat.size(); i++) {
                latlng = new LatLng(Double.parseDouble(pLat.get(i)),
                        Double.parseDouble(pLng.get(i)));
                MarkerOptions markerOptions = new MarkerOptions().position(latlng).title(pNames.get(i) + " - " + pEmail.get(i));
                mMap.addMarker(markerOptions);
            }
        }

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))
                .title("You are here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        //move the camera over my current location
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //go to another activity with marker's name
//        Intent intent = new Intent();
        String tag = marker.getTitle();
        if(tag.equals("You are here"))  {
            Toast.makeText(getApplicationContext(), "You are here", Toast.LENGTH_SHORT).show();
            return true;
        }

        String professionalEmail = tag.split("- ")[1];
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("professionalEmail", professionalEmail);
        startActivity(intent);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}