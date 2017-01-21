package com.biniam.android.gpsbasedpongandroidgame;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class AndrongActivity extends Activity implements SensorEventListener
{
   private AndrongSurfaceView pongSurfaceView;
   private SensorManager sensorManager;
   AndrongThread androidPongThread;
   TextView scoreText;
   TextView speedText;
   TextView liveText;
   LinearLayout finalBoard;
   Button playAgain;
   /**
    * Called when the activity is first created.
    */
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      requestWindowFeature(Window.FEATURE_NO_TITLE);
      setContentView(R.layout.main);
      setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      scoreText =(TextView)findViewById(R.id.socre);
      speedText = (TextView)findViewById(R.id.speed);
      liveText = (TextView)findViewById(R.id.lives);
      pongSurfaceView = (AndrongSurfaceView) findViewById(R.id.androng);
      //pongSurfaceView.setTextView((TextView) findViewById(R.id.text));
      finalBoard = (LinearLayout)findViewById(R.id.finalScene);
      playAgain  = (Button)findViewById(R.id.Retry);
      playAgain.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             androidPongThread.playAgian();
            finalBoard.setVisibility(View.INVISIBLE);
         }

      });
      pongSurfaceView.setLinear(finalBoard);
      pongSurfaceView.setScoreText(scoreText);
      pongSurfaceView.setLiveText(liveText);
      sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
      androidPongThread = pongSurfaceView.getAndroidPongThread();

       Bundle b = getIntent().getExtras();
       if(b.getInt("playerType") == 1){
           androidPongThread.doStart1p();
       }
       if(b.getInt("playerType") == 2){
          Log.e("multiplayer selected", 1 + "  androng");
           androidPongThread.doStart2p();
          final Thread thread = new Thread(new Runnable() {
             @Override
             public void run() {
                try {
                   DataInputStream fromServer = new DataInputStream(Constants.getSocket().getInputStream());
                   DataOutputStream toServer = new DataOutputStream(Constants.getSocket().getOutputStream());
                   Log.e("socket established", 1 + "  androng");
                   while(!Constants.gameOver) {
                      toServer.writeInt((int) androidPongThread.getXValue());
                      int k = 0;
                      Log.e("x sent to server", androidPongThread.getXValue() + "  androng");
                      k = fromServer.readInt();
                      androidPongThread.setOpp(k);
                      Log.e("x received from server", k + "  androng");
                      //Thread.sleep(100);
                   }

                }
                catch (IOException e) {
                   e.printStackTrace();
                }
             }
          });
          thread.start();

       }



   }

   private static final int MENU_PAUSE = 4;
   private static final int MENU_RESUME = 5;
   private static final int MENU_START_1P = 6;
   private static final int MENU_START_2P = 7;
   private static final int MENU_START_0P = 8;
   private static final int MENU_SHOWINFO = 10;
   private static final int MENU_SOUND_ON = 11;

   @Override
   public boolean onCreateOptionsMenu(Menu menu)
   {
      super.onCreateOptionsMenu(menu);
      menu.add(0, MENU_START_1P, 0, R.string.menu_start_1p);
      menu.add(0, MENU_START_2P, 0, R.string.menu_start_2p);
      menu.add(0, MENU_START_0P, 0, R.string.menu_start_0p);
      menu.add(0, MENU_PAUSE, 0, R.string.menu_pause);
      menu.add(0, MENU_RESUME, 0, R.string.menu_resume);
      menu.add(0, MENU_SOUND_ON, 0, R.string.menu_sound);
      menu.add(0, MENU_SHOWINFO, 0, R.string.menu_info);
      return true;
   }

   @Override
   public boolean onMenuOpened(int featureId, Menu menu)
   {
      AndrongThread androidPongThread = pongSurfaceView.getAndroidPongThread();
      super.onMenuOpened(featureId, menu);
      androidPongThread.pause();
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(MenuItem item)
   {
      AndrongThread androidPongThread = pongSurfaceView.getAndroidPongThread();
      switch (item.getItemId())
      {
         case MENU_START_1P:
            androidPongThread.doStart1p();
            return true;
         case MENU_START_2P:
            androidPongThread.doStart2p();
            return true;
         case MENU_START_0P:
            androidPongThread.doStart0p();
            return true;
         case MENU_PAUSE:
            androidPongThread.pause();
            return true;
         case MENU_RESUME:
            androidPongThread.unpause();
            return true;
         case MENU_SHOWINFO:
            androidPongThread.toggleDiagnosticInformation();
            return true;
         case MENU_SOUND_ON:
            androidPongThread.toggleSound();
            return true;
      }

      return false;
   }

   /**
    * Invoked when the Activity loses user focus.
    */
   @Override
   protected void onPause()
   {
      super.onPause();
      sensorManager.unregisterListener(this);
      AndrongThread androidPongThread = pongSurfaceView.getAndroidPongThread();
      androidPongThread.pause(); // pause game when Activity pauses
   }

   @Override
   protected void onResume()
   {
      super.onResume();
      sensorManager.registerListener(this,
              sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
              SensorManager.SENSOR_DELAY_NORMAL);
      AndrongThread androidPongThread = pongSurfaceView.getAndroidPongThread();
      androidPongThread.resumeGame();
   }

   protected void onDestroy()
   {
      super.onDestroy();
      SoundManager.cleanup();
   }

   @Override
   public void onSensorChanged(SensorEvent event) {

      if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
         float[] values = event.values;

         androidPongThread.setBattPosition(values[0],values[0]*20,values[0] * 20, values[0]*20);
      }
   }

   @Override
   public void onAccuracyChanged(Sensor sensor, int accuracy) {

   }
}
