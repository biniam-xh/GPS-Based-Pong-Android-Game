package com.biniam.android.gpsbasedpongandroidgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


public class FourthActivity extends ActionBarActivity {
    Intent receivedIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        receivedIntent = getIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fourth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void openSinglePlayerGame(View view){
        Intent intent = new Intent(this, FifthActivity.class);
        intent.putExtra("playerType",1);
        //double[] upperLeftLoc = receivedIntent.getExtras().getDoubleArray("upperLeftLoc");
        //String s = "sssssssssss"+ receivedIntent.getDoubleArrayExtra("upperLeftLoc")[0];
        //Toast.makeText(this, "Ssssssssssa" , Toast.LENGTH_LONG).show();
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        startActivity(intent);
    }
    public void openMultiPlayerGmae(View view){
        Intent intent = new Intent(this, FifthActivity.class);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        intent.putExtra("playerType",2);
        startActivity(intent);
    }
    public void navigateToGoogleMaps(View view){
        Intent intent = new Intent(this, SecondActivity.class);

        startActivity(intent);

    }
}
