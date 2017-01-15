package com.biniam.android.gpsbasedpongandroidgame;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewSwitcher;


public class FifthActivity extends ActionBarActivity {

    public int playerType;
    public Intent receivedIntent;
    public ViewSwitcher switcher;
    public ViewSwitcher switcher3;
    public Button player1;
    public Button player2;
    public EditText securityCode;
    public Button done;
    private static final int REFRESH_SCREEN = 1;
    public Button ok;
    public EditText securityCode2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        playerType = getIntent().getExtras().getInt("playerType");
        receivedIntent = getIntent();


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
        Intent intent = new Intent(this, SixthActivity.class);
        intent.putExtra("mode",1);
        intent.putExtra("playerType", playerType);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        Bundle b = getIntent().getExtras();
        int type = b.getInt("playerType");
        if(type == 2){
            multiPlayerSelected();
        }
        else{
            startActivity(intent);
        }

    }
    public void selectProfessionalMode(View view){
        Intent intent = new Intent(this, SixthActivity.class);
        intent.putExtra("mode",2);
        intent.putExtra("playerType", playerType);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        Bundle b = getIntent().getExtras();
        int type = b.getInt("playerType");
        if(type == 2){
            multiPlayerSelected();
        }
        else{
            startActivity(intent);
        }
    }
    public void selectWorldClassMode(View view){
        Intent intent = new Intent(this, SixthActivity.class);
        intent.putExtra("mode",3);
        intent.putExtra("playerType", playerType);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        Bundle b = getIntent().getExtras();
        int type = b.getInt("playerType");
        if(type == 2){
            multiPlayerSelected();
        }
        else{
            startActivity(intent);
        }
    }
    public void player1Selected(View view){
        new AnimationUtils();
        switcher.setAnimation(AnimationUtils.makeInAnimation
                (getBaseContext(), true));
        switcher.showNext();
    }
    public void player2Selected(View view){
        setContentView(R.layout.player2_security_check);
    }
    public void setSecurityCode(View view){
        String code = securityCode.getText().toString();
        setContentView(R.layout.player1_notification);
        switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher2);
        startScan(code, switcher);
    }
    public void startScan(String s, ViewSwitcher switcher) {

        new Thread() {

            public void run() {

                try{
                    // This is just a tmp sleep so that we can emulate something loading
                    Thread.sleep(5000);
                    // Use this handler so than you can update the UI from a thread
                    Refresh.sendEmptyMessage(REFRESH_SCREEN);
                } catch(Exception e){
                }
            }
        }.start();
    }

    // Refresh handler, necessary for updating the UI in a/the thread
    Handler Refresh = new Handler(){
        public void handleMessage(Message msg) {

            switch(msg.what){

                case REFRESH_SCREEN:
                    switcher.showNext();
                    // To go back to the first view, use switcher.showPrevious()
                    break;

                default:
                    break;
            }
        }
    };
    public void multiPlayerSelected(){
        setContentView(R.layout.multi_player_type);
        switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher);


        player1 = (Button) findViewById(R.id.player1);
        player2 = (Button) findViewById(R.id.player2);
        securityCode = (EditText) findViewById(R.id.securityCode);
        done = (Button) findViewById(R.id.done);
    }
    public void confirmCode(View view){
        setContentView(R.layout.player2_security_check);
        securityCode2 = (EditText) findViewById(R.id.securityCode2);
        ok = (Button) findViewById(R.id.ok);
        String code = securityCode2.getText().toString();
        switcher3 = (ViewSwitcher) findViewById(R.id.ViewSwitcher3);
        switcher3.showNext();
    }

}
