package com.example.mapas;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.libraries.places.api.Places;

public class MainActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

        private static boolean LOCATION_PERMISSION_REQUEST_CODE = false;
        public GoogleMap mapa;
        public LatLng localizacao = new LatLng(-23.951137, -46.339025);
        private Button btMinhaPosicao;
        private GeoDataClient geoDataClient;
        private FusedLocationProviderClient mFusedLocationProviderClient;

    public MainActivity() {
    }
//same thing as before

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapinha);
        mapFragment.getMapAsync(MainActivity.this);
        geoDataClient = Places.getGeoDataClient(MainActivity.this, null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        metodoBotao();

    }
    private void metodoBotao(){
        btMinhaPosicao = (Button)findViewById(R.id.btnPosit);
        btMinhaPosicao.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                enableMyLocation();
                atualizaSuaLocalizacao();
            }
        });

    }
    private void enableMyLocation(){
        // can u check typing here
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))
        {
                LOCATION_PERMISSION_REQUEST_CODE = PermissionUtils.validate(this, 1, Manifest.permission.ACCESS_FINE_LOCATION);
                LOCATION_PERMISSION_REQUEST_CODE = PermissionUtils.validate(this, 1, Manifest.permission.ACCESS_COARSE_LOCATION);
                atualizaSuaLocalizacao();
        }

    }
    ////////////////////////////////////////////////////// :(
    private void atualizaSuaLocalizacao(){
        try {
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    mapa.addMarker(new MarkerOptions().position(latLng).title("Say hello!!"));
                    //mapa.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                    mapa.animateCamera(update);
                    mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                public void onStatusChanged(String provider, int status, Bundle extras){

                }
                public void onProviderEnabled(String provider){

                }
                public void onProviderDisabled(String provider){

                }

            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) locationListener);

        }
        catch (SecurityException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(localizacao, 18);
        mapa.animateCamera(update);

        Circle circle = mapa.addCircle(new CircleOptions()
            .center(localizacao)
            .radius(100)
            .strokeColor(Color.RED)
            .fillColor(Color.TRANSPARENT));

        mapa.addMarker(new MarkerOptions().position(localizacao).title("SFC"));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
