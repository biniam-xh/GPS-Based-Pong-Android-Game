package com.biniam.android.gpsbasedpongandroidgame;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class SixthActivity extends AppCompatActivity implements OnMapReadyCallback
         {

    public GoogleMap mMap;
    public Intent receivedIntent;
    public Intent intent;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
             Context c ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);

        c = getApplicationContext();
        receivedIntent = getIntent();
        intent = new Intent(this,SeventhActivity.class);

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
                    showMap();
                    takeSnapshot();

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
        double zoomLevel = b.getDouble("zoomLevel");
        int playerType = b.getInt("playerType");
        int mode = b.getInt("mode");

        LatLng upperLeftLatLng = new LatLng(upperLeftLoc[0], upperLeftLoc[1]);
        LatLng upperRightLatLng = new LatLng(upperRightLoc[0],upperRightLoc[1]);
        LatLng lowerRightLatLng = new LatLng(lowerRightLoc[0],lowerRightLoc[1]);
        LatLng lowerLeftLatLng = new LatLng(lowerLeftLoc[0],lowerLeftLoc[1]);


        LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                .include(upperLeftLatLng)
                .include(upperRightLatLng)
                .include(lowerRightLatLng)
                .include(lowerLeftLatLng);
        LatLng centerLatLng = boundsBuilder.build().getCenter();
        double[] center = {centerLatLng.latitude,centerLatLng.longitude};


       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(upperLeftLoc[0], upperLeftLoc[1]), 20));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 0));

        disableGestures();


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
             public void disableGestures(){
                 mMap.getUiSettings().setMapToolbarEnabled(false);
                 mMap.getUiSettings().setZoomGesturesEnabled(false);
                 mMap.getUiSettings().setScrollGesturesEnabled(false);
                 mMap.getUiSettings().setTiltGesturesEnabled(false);
                 mMap.getUiSettings().setRotateGesturesEnabled(false);
             }

             private void takeSnapshot() {
                 //Toast.makeText(c, "1", Toast.LENGTH_LONG).show();
                 if (mMap == null) {
                     return;
                 }

                 final GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

                     @Override
                     public void onSnapshotReady(Bitmap snapshot) {
                         // Callback is called from the main thread, so we can modify the ImageView safely.

                         Toast.makeText(c, "1", Toast.LENGTH_LONG).show();
                         intent.putExtra("bitmap",createImageFromBitmap(snapshot));
                         Toast.makeText(c, "2", Toast.LENGTH_LONG).show();
                         intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
                         startActivity(intent);

                     }
                 };

                 mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                         @Override
                         public void onMapLoaded() {
                             mMap.snapshot(callback);
                         }
                     });
                 mMap.snapshot(callback);

             }
             public String createImageFromBitmap(Bitmap bitmap) {
                 String fileName = "myImage";//no .png or .jpg needed
                 try {
                     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                     FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
                     fo.write(bytes.toByteArray());
                     // remember close file output
                     fo.close();
                 } catch (Exception e) {
                     e.printStackTrace();
                     fileName = null;
                 }
                 return fileName;
             }

}
