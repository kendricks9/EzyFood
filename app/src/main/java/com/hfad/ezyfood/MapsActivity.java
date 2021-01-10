package com.hfad.ezyfood;

import android.os.Build;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        LatLng alam_sutera = new LatLng(-6.222492, 106.648953);
        LatLng bsd = new LatLng(-6.305984267861596, 106.64178335251724);
        LatLng bintaro = new LatLng(-6.285772520747877, 106.72641318937653);
        mMap.addMarker(new MarkerOptions().position(alam_sutera).title("ezyFood Alam Sutera"));
        mMap.addMarker(new MarkerOptions().position(bsd).title("ezyFood BSD"));
        mMap.addMarker(new MarkerOptions().position(bintaro).title("ezyFood Bintaro"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(alam_sutera));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }
}