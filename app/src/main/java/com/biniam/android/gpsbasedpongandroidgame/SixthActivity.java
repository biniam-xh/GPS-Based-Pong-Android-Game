package com.biniam.android.gpsbasedpongandroidgame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
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

public class SixthActivity extends AppCompatActivity implements OnMapReadyCallback
         {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Intent receivedIntent;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
             private static final LatLng BRISBANE = new LatLng(8, 4);

             private static final LatLng ADELAIDE = new LatLng(1, 1);

             private static final LatLng PERTH = new LatLng(1, 4);

             private static final LatLng DARWIN = new LatLng(8, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        receivedIntent = getIntent();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map6);
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

        final View mapView = getSupportFragmentManager().findFragmentById(R.id.map6).getView();
        if (mapView.getViewTreeObserver().isAlive()) {
            mapView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation") // We use the new method when supported
                @SuppressLint("NewApi") // We check which build version we are using.
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                    LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                            .include(PERTH)
                            .include(ADELAIDE)
                            .include(DARWIN)
                            .include(BRISBANE);

                    setMarker(PERTH);
                    setMarker(ADELAIDE);
                    setMarker(DARWIN);
                    setMarker(BRISBANE);
                    LatLngBounds test = new LatLngBounds(ADELAIDE,BRISBANE);

                    //mMap.setLatLngBoundsForCameraTarget(test);

                    // Move camera to show all markers and locations
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 10));

                    LatLng[] POLYGON = new LatLng[]{
                            ADELAIDE,PERTH,BRISBANE,DARWIN

                    };
                    mMap.addPolygon(new PolygonOptions()
                            .add(POLYGON)

                            .strokeColor(Color.RED)
                            .strokeWidth(5));
                }
            });
        }
    }
    public void showMap(){

        /////////
        Bundle b=this.getIntent().getExtras();
        double[] upperLeftLoc = b.getDoubleArray("upperLeftLoc");
        double[] upperRightLoc = b.getDoubleArray("upperRightLoc");
        double[] lowerRightLoc = b.getDoubleArray("lowerRightLoc");
        double[] lowerLeftLoc = b.getDoubleArray("lowerLeftLoc");

        LatLng upperLeftLatLng = new LatLng(upperLeftLoc[0], upperLeftLoc[1]);
        LatLng upperRightLatLng = new LatLng(upperRightLoc[0],upperRightLoc[1]);
        LatLng lowerRightLatLng = new LatLng(lowerRightLoc[0],lowerRightLoc[1]);
        LatLng lowerLeftLatLng = new LatLng(lowerLeftLoc[0],lowerLeftLoc[1]);
        String s = "sssssssssss"+ upperLeftLoc[0];
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();

        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                .include(upperLeftLatLng)
                .include(upperRightLatLng)
                .include(lowerRightLatLng)
                .include(lowerLeftLatLng);
        LatLng centerLatLng = boundsBuilder.build().getCenter();
        double[] center = {centerLatLng.latitude,centerLatLng.longitude};


       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(upperLeftLoc[0], upperLeftLoc[1]), 20));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 0));

        /*LatLng[] POLYGON = new LatLng[]{
                upperLeftLatLng, upperRightLatLng,
                lowerRightLatLng, lowerLeftLatLng};

        mMap.addPolygon(new PolygonOptions()
                .add(POLYGON)
                .fillColor(Color.CYAN)
                .strokeColor(Color.BLUE)
                .strokeWidth(5));*/

    }
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
             public void setMarker(LatLng latLng){
                 //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                 MarkerOptions markerOptions = new MarkerOptions();
                 markerOptions.position(latLng);
                 //markerOptions.title("Current Position");
                 markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                 Marker newMarker = mMap.addMarker(markerOptions);
             }


}
