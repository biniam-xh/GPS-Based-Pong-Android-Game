package com.biniam.android.gpsbasedpongandroidgame;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;


public class FirstActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    SettingDialogFragment settingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList<String>();


        // connect to the server
        //conctTask = new connectTask();
        //conctTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            settingDialog = new SettingDialogFragment();
            settingDialog.show(getFragmentManager(),"settings");


        }

        return super.onOptionsItemSelected(item);
    }
    public void showCurrentLocation(View view){
        Intent intent = new Intent(this, SecondActivity.class);

        startActivity(intent);
    }




}
