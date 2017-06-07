package com.adida.aka.studygooglemaptt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner spinnerType;
    ArrayList<String> dsType;
    ArrayAdapter<String> adapterType;
    ProgressDialog dialog;

    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng lng = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if(mMap != null){
                mMap.clear();
                Marker marker = mMap.addMarker(new
                    MarkerOptions().position(lng)
                        .title("Chổ tao đang ở nè").snippet("Nơi tập trung các cao thủ võ lâm..."));
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(lng, 16.0f));
        }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        spinnerType = (Spinner) findViewById(R.id.spinner);
        dsType = new ArrayList<>();
        dsType.addAll(Arrays.asList(getResources().getStringArray(R.array.arrayType)));
        adapterType = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, dsType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Thong bao");
        dialog.setMessage("Loading data, please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    private void addEvents() {
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyCheDoHienThi(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void xuLyCheDoHienThi(int position) {
        switch (position) {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 4:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }

    public void setUpMap() {

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        // Loaded data : Da tai xong du lieu moi xu ly
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                dialog.dismiss();

//                //Cho Tan Chanh Hiep (10.860144, 106.617964)
//                LatLng tanChanhHiepMarket = new LatLng(10.860144, 106.617964);
//                mMap.addMarker(new
//                        MarkerOptions().position(tanChanhHiepMarket)
//                        .title("Cho Tan Chanh Hiep").snippet("Noi tap chung mua ban trao doi"));
//
//                //min:0-> max:18
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tanChanhHiepMarket, 13));


                //Auto load your location
                mMap.setOnMyLocationChangeListener(listener);

            }
        });
    }
}
