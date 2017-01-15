package com.biniam.android.gpsbasedpongandroidgame;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity implements OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener

    {

        //private GoogleMap mMap; // Might be null if Google Play services APK is not available.
        private GoogleMap mMap;
        GoogleApiClient mGoogleApiClient;
        Location myLastLocation;
        Marker currentLocationMarker;
        LocationRequest myLocationRequest;
        ArrayList rectangleCordinates;
        Location upperLeft;
        Location upperRight;
        Location lowerRight;
        Location lowerLeft;
        Button upperLeftButton;
        Button upperRightButton;
        Button lowerRightButton;
        Button lowerLeftButton;
        Marker newMarker;
        Intent intent;
        boolean allFixed = false;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            //sets the layout of the activity
            setContentView(R.layout.activity_third);

            upperLeftButton = (Button) findViewById(R.id.upperLeftButton);
            upperRightButton = (Button) findViewById(R.id.upperRightButton);
            lowerRightButton = (Button) findViewById(R.id.lowerRightButton);
            lowerLeftButton = (Button) findViewById(R.id.lowerLeftButton);

            //disable all the buttons except the first one.
            upperLeftButton.setEnabled(true);
            upperRightButton.setEnabled(false);
            upperRightButton.setTextColor(Color.GRAY);
            lowerRightButton.setEnabled(false);
            lowerRightButton.setTextColor(Color.GRAY);
            lowerLeftButton.setEnabled(false);
            lowerLeftButton.setTextColor(Color.GRAY);

            intent = new Intent(this, FourthActivity.class);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);

            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        myLocationRequest = new LocationRequest();
        myLocationRequest.setInterval(1000);
        myLocationRequest.setFastestInterval(1000);
        myLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, myLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        myLastLocation = location;
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currentLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

        //stop location updates
        //if (mGoogleApiClient != null) {
        //    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        //}

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    // return to welcome page
                    //Intent intent = new Intent(this, FirstActivity.class);
                    //startActivity(intent);
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }
        public void fixUpperLeftCorner(View view){
            if(myLastLocation != null){
               upperLeft = myLastLocation;

               upperLeftButton.setEnabled(false);
               upperRightButton.setEnabled(true);
                upperRightButton.setTextColor(Color.WHITE);

                setMarker(upperLeft);
                upperLeftButton.setText("FIXED");
            }
        }
        public void fixUpperRightCorner(View view){
            if(myLastLocation != null){
                upperRight = myLastLocation;

                upperRightButton.setEnabled(false);
                lowerRightButton.setEnabled(true);
                lowerRightButton.setTextColor(Color.WHITE);

                setMarker(upperRight);
                upperRightButton.setText("FIXED");
            }
        }
        public void fixLowerRightCorner(View view){
            if(myLastLocation != null){
                lowerRight = myLastLocation;

                lowerRightButton.setEnabled(false);
                lowerLeftButton.setEnabled(true);
                lowerLeftButton.setTextColor(Color.WHITE);

                setMarker(lowerRight);
                lowerRightButton.setText("FIXED");
            }
        }
        public void fixLowerLeftCorner(View view){

                lowerLeft = myLastLocation;

                lowerLeftButton.setEnabled(false);

                lowerLeftButton.setText("FIXED");

                setMarker(lowerLeft);

            if(myLastLocation != null){
                if (mGoogleApiClient != null) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                }


                //create perfect rectangle
                if(upperLeft.getLongitude()< lowerLeft.getLongitude()){
                    upperLeft.setLongitude(lowerLeft.getLongitude());
                }
                else{
                    lowerLeft.setLongitude(upperLeft.getLongitude());
                }
                if(upperRight.getLongitude()<lowerRight.getLongitude()){
                    upperRight.setLongitude(lowerRight.getLongitude());
                }
                else{
                    lowerRight.setLongitude(upperRight.getLongitude());
                }
                if(upperLeft.getLatitude()<upperRight.getLatitude()){
                    upperLeft.setLatitude(upperRight.getLatitude());
                }
                else{
                    upperRight.setLatitude(upperLeft.getLatitude());
                }
                if(lowerLeft.getLatitude()<lowerRight.getLatitude()){
                    lowerLeft.setLatitude(lowerRight.getLatitude());
                }
                else{
                    lowerRight.setLatitude(lowerLeft.getLatitude());
                }
                 /////////


                double[] upperLeftLoc = {upperLeft.getLatitude(),upperLeft.getLongitude()};
                double[] upperRightLoc = {upperRight.getLatitude(),upperRight.getLongitude()};
                double[] lowerRightLoc = {lowerRight.getLatitude(),lowerRight.getLongitude()};
                double[] lowerLeftLoc = {lowerLeft.getLatitude(),lowerLeft.getLongitude()};

                Bundle b=new Bundle();

                b.putDoubleArray("upperLeftLoc", upperLeftLoc );
                b.putDoubleArray("upperRightLoc", upperRightLoc);
                b.putDoubleArray("lowerRightLoc", lowerRightLoc);
                b.putDoubleArray("lowerLeftLoc",lowerLeftLoc);


                intent.putExtras(b);

                allFixed = true;
                /////adding box
                LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                        .include(new LatLng(upperLeft.getLatitude(),upperLeft.getLongitude()))
                        .include(new LatLng(upperRight.getLatitude(),upperRight.getLongitude()))
                        .include(new LatLng(lowerRight.getLatitude(),lowerRight.getLongitude()))
                        .include(new LatLng(lowerLeft.getLatitude(),lowerLeft.getLongitude()));
               // boundsBuilder.build().
                // Move camera to show all markers and locations
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 10));
                b.putDouble("zoomLevel", mMap.getCameraPosition().zoom);
                //LatLngBounds curScreen = mMap.getProjection()
                 //       .getVisibleRegion().latLngBounds;
/*
                double lowestLat = 0;
                double lowestLong = 0;
                double highestLat = 0;
                double highestLong = 0;

                double [] lats = {upperLeft.getLatitude(),upperRight.getLatitude(),lowerRight.getLatitude(),lowerLeft.getLatitude()};
                lowestLat = getHighest_Lowest(lats)[0];
                highestLat = getHighest_Lowest(lats)[1];

                double [] longs = {upperLeft.getLongitude(),upperRight.getLongitude(),lowerRight.getLongitude(),lowerLeft.getLongitude()};
                lowestLong = getHighest_Lowest(longs)[0];
                highestLong = getHighest_Lowest(longs)[1];
*/


                LatLng[] POLYGON = new LatLng[]{
                        new LatLng(upperLeft.getLatitude(),upperLeft.getLongitude()),
                        new LatLng(upperRight.getLatitude(),upperRight.getLongitude()),
                        new LatLng(lowerRight.getLatitude(),lowerRight.getLongitude()),
                        new LatLng(lowerLeft.getLatitude(),lowerLeft.getLongitude())

                };

                mMap.addPolygon(new PolygonOptions()
                        .add(POLYGON)
                        .strokeColor(Color.CYAN)
                        .strokeWidth(5));
                 ////

                Toast.makeText(this, "SUCCESSFULLY FIXED THE LOCATION COORDINATES FOR THE PLAYGROUND", Toast.LENGTH_LONG).show();
            }
        }
        public void navigateToMap(View view){
                if(allFixed){
                    allFixed = true;
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "PLEASE FIX LOCATION COORDINATES FOR THE PLAYGROUND", Toast.LENGTH_SHORT).show();
                }


        }
        public void setMarker(Location location){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            //markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            Marker newMarker = mMap.addMarker(markerOptions);
        }
        public double[] getHighest_Lowest(double[] x){
            double highest = x[0];
            double lowest = x[0];
            for(int i=1; i<x.length; i++){
                if(x[i]> highest){
                    highest = x[i];
                }
            }
            for(int i=1; i<x.length; i++){
                if(x[i]> lowest){
                    lowest = x[i];
                }
            }
            double[] result = {lowest,highest};
            return result;
        }

        
}


