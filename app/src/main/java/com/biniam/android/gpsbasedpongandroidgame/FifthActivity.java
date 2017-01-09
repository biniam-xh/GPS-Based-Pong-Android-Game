package com.biniam.android.gpsbasedpongandroidgame;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class FifthActivity extends ActionBarActivity {

    int playerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        playerType = getIntent().getExtras().getInt("playerType");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fifth, menu);
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
    public void selectAmateurMode(View view){
        Intent intent = new Intent(this, FifthActivity.class);
        intent.putExtra("mode",1);
        intent.putExtra("playerType", playerType);
        startActivity(intent);
    }
    public void selectProfessionalMode(View view){
        Intent intent = new Intent(this, FifthActivity.class);
        intent.putExtra("mode",2);
        intent.putExtra("playerType", playerType);
        startActivity(intent);
    }
    public void selectWorldClassMode(View view){
        Intent intent = new Intent(this, FifthActivity.class);
        intent.putExtra("mode",3);
        intent.putExtra("playerType", playerType);
        startActivity(intent);
    }
}
