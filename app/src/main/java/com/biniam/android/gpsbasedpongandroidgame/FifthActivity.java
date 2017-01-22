package com.biniam.android.gpsbasedpongandroidgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class FifthActivity extends ActionBarActivity {

    public Context context;
    public int playerType;
    public Intent receivedIntent;
    public ViewSwitcher switcher;
    public ViewSwitcher switcher3;
    public Button player1;
    public Button player2;
    public EditText securityCode;
    public Button done;
    private static final int CONNECTION_ESTABLISHED = 1;
    private static final int CONNECTION_FAILED = 2;
    private static final int CODE_MISMATCH = 3;
    public Button ok;
    public EditText securityCode2;
    public boolean player2_On = false;
    int code = 0;
    public int player = 0;
    public int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_fifth);
        playerType = getIntent().getExtras().getInt("playerType");
        receivedIntent = getIntent();
        context = getApplicationContext();


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
            SettingDialogFragment settingDialog = new SettingDialogFragment();
            settingDialog.show(getFragmentManager(),"settings");
        }

        return super.onOptionsItemSelected(item);
    }
    public void selectAmateurMode(View view){
        Intent intent = new Intent(this, AndrongActivity.class);
        intent.putExtra("mode",1);
        intent.putExtra("playerType", playerType);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        Bundle b = getIntent().getExtras();
        int type = b.getInt("playerType");
        VelocityGenerator.MinimumXSpeed = 300;
        VelocityGenerator.MinimumYSpeed = 300;
        if(type == 2){
            multiPlayerSelected();
        }
        else{
            startActivity(intent);
        }

    }
    public void selectProfessionalMode(View view){
        Intent intent = new Intent(this, AndrongActivity.class);
        intent.putExtra("mode",2);
        intent.putExtra("playerType", playerType);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        Bundle b = getIntent().getExtras();
        int type = b.getInt("playerType");
        VelocityGenerator.MinimumXSpeed = 500;
        VelocityGenerator.MinimumYSpeed = 500;
        if(type == 2){
            multiPlayerSelected();
        }
        else{
            startActivity(intent);
        }
    }
    public void selectWorldClassMode(View view){
        Intent intent = new Intent(this, AndrongActivity.class);
        intent.putExtra("mode",3);
        intent.putExtra("playerType", playerType);
        intent.fillIn(receivedIntent, Intent.FILL_IN_DATA);
        Bundle b = getIntent().getExtras();
        int type = b.getInt("playerType");
        VelocityGenerator.MinimumXSpeed = 700;
        VelocityGenerator.MinimumYSpeed = 700;
        if(type == 2){
            multiPlayerSelected();
        }
        else{
            startActivity(intent);
        }
    }
    public void player1Selected(View view){
        new AnimationUtils();
        player = 1;
        switcher.setAnimation(AnimationUtils.makeInAnimation
                (getBaseContext(), true));
        switcher.showNext();
    }
    public void player2Selected(View view){
        player = 2;
        setContentView(R.layout.player2_security_check);
    }
    public void setSecurityCode(View view){

        if(securityCode.getText().toString().length() == 4){
            code = Integer.parseInt(securityCode.getText().toString());
            setContentView(R.layout.player_notification);
            player = 1;
            //send socket request to the server
            Client c = new Client();
            switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher2);
            startScan(code, switcher);
        }


        else{
            Toast.makeText(getApplicationContext(), "Security Code must be a length of 4 numbers", Toast.LENGTH_SHORT).show();
        }
    }
    public void startScan(int s, ViewSwitcher switcher) {

        new Thread() {

            public void run() {

                try{
                    // This is just a tmp sleep so that we can emulate something loading
                    while(!player2_On){

                    }
                    if(player == 2){
                        while(status == 0){

                        }
                        if(status == 1){
                            Refresh.sendEmptyMessage(CONNECTION_ESTABLISHED);
                        }
                        else if(status == -1){
                            Refresh.sendEmptyMessage(CODE_MISMATCH);
                        }
                        else if(status == 1){
                            while(status == 1){

                            }
                        }
                    }
                    else{
                        Refresh.sendEmptyMessage(CONNECTION_ESTABLISHED);
                    }
                    //Thread.sleep(5000);
                    // Use this handler so than you can update the UI from a thread
                    Refresh.sendEmptyMessage(CONNECTION_FAILED);
                } catch(Exception e){


                }
            }
        }.start();
    }

    // Refresh handler, necessary for updating the UI in a/the thread
    Handler Refresh = new Handler(){
        public void handleMessage(Message msg) {

            switch(msg.what){

                case CONNECTION_ESTABLISHED:

                    // To go back to the first view, use switcher.showPrevious()
                    break;
                case CODE_MISMATCH:

                    // To go back to the first view, use switcher.showPrevious()
                    break;
                case CONNECTION_FAILED:

                    // To go back to the first view, use switcher.showPrevious()
                    break;

                default:
                    Log.e("notification", status + "  message");
                    switcher.showNext();
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

        securityCode2 = (EditText) findViewById(R.id.securityCode2);
        ok = (Button) findViewById(R.id.ok);
        player = 2;

        if(securityCode2.getText().toString().length() == 4){
            code = Integer.parseInt(securityCode2.getText().toString());
            player2_On = true;
            player = 2;

            setContentView(R.layout.player_notification);
            TextView t = (TextView) findViewById(R.id.waitingText);
            t.setText("Connecting");
            TextView t2 = (TextView) findViewById(R.id.succesText);
            t2.setText("Unable to Connect");
            //send socket request to the server
            Client c = new Client();
            switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcher2);
            startScan(code, switcher);
        }
        else{
            Toast.makeText(getApplicationContext(), "Security Code must be a length of 4 numbers", Toast.LENGTH_SHORT).show();
        }

    }

    class Client{

        private DataInputStream fromServer;
        private DataOutputStream toServer;
        SharedPreferences sharedpreferences = getSharedPreferences("com.biniam.android.gpsbasedpongandroidgame_preference_file_key",MODE_PRIVATE);
        public String host = sharedpreferences.getString("address", "192.168.202.1");
        public String error = "works";
        boolean playerOn  = true;
        boolean firstCon = false;
        int connectionWithPlayer = 0;
        boolean isConnected = false;
        public Client(){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    connectToServer();
                }
            });
            thread.start();
        }

        public void connectToServer(){
            Log.e("sharedpreference", host + "");
            //Toast.makeText(getApplicationContext(), "Connecting", Toast.LENGTH_SHORT).show();

            int timeout = 10000;
            int port = sharedpreferences.getInt("port",44345 );
            Log.e("sharedpreference", port + "");
            final Socket socket;
            InetSocketAddress socketAddress = new InetSocketAddress(host, port);

            try {
                socket = new Socket(host, port);
                fromServer = new DataInputStream(socket.getInputStream());
                toServer = new DataOutputStream(socket.getOutputStream());
                while(playerOn){
                    if(!firstCon){
                        int connection = fromServer.readInt();
                        Log.e("connection", connection + "  IS FROM SERVER");
                        firstCon = true;
                        //Toast.makeText(getApplicationContext(), "Connection Established", Toast.LENGTH_SHORT).show();
                        //connection established
                        if(player == 1){
                            toServer.writeInt(1);
                            toServer.writeInt(code);



                            Thread newThread = new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    try{
                                        ServerSocket serverSocket = new ServerSocket(5534);


                                        while(!isConnected){
                                            //toServer.writeInt(3);
                                            Log.e("server", status + "  started");
                                            Socket playerSoc = serverSocket.accept();

                                            InetAddress inetAddress = playerSoc.getInetAddress();

                                            DataInputStream fromPlayer = new DataInputStream(playerSoc.getInputStream());
                                            DataOutputStream toPlayer = new DataOutputStream(playerSoc.getOutputStream());

                                            //toPlayer.writeInt(1);
                                            try {
                                                //create socket connection with player2

                                                Bitmap bmp = BitmapFactory.decodeStream(getApplicationContext().openFileInput("myImage"));
                                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                                byte[] array = bos.toByteArray();

                                                toPlayer.writeInt(array.length);
                                                toPlayer.write(array, 0, array.length);
                                                isConnected = true;

                                                Log.e("Image", array.length + "  sent");

                                                int response = fromPlayer.readInt();
                                                if(response == 1){
                                                    // image sent and received
                                                    Log.e("Image", response + "  sent");

                                                    Constants.setSocket(socket);
                                                    //toServer = new DataOutputStream(socket.getOutputStream());
                                                    toServer.writeInt(1);
                                                    Handler handler = new Handler(Looper.getMainLooper());
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            playerOn = false;

                                                            Intent intent = new Intent(context, AndrongActivity.class);
                                                            intent.fillIn(receivedIntent,Intent.FILL_IN_DATA);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            context.startActivity(intent);
                                                            finish();
                                                        }
                                                    });

                                                    Log.e("ssssssssssssssss", response + "started");

                                                    //playerSoc.close();
                                                    //continue to game

                                                }


                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                                Log.e("ssssssssssssssss", e + "started");
                                            }


                                        }
                                    }
                                    catch(Exception ex){ ex.printStackTrace();}
                                }


                            });
                            newThread.start();
                        }
                        if(player == 2){
                            toServer.writeInt(2);
                            toServer.writeInt(code);
                        }
                    }
                    else{

                        if(!player2_On){
                            connectionWithPlayer = fromServer.readInt();
                            if(connectionWithPlayer == 2){
                                player2_On = true;
                                //player 2 joined

                                //send bitmap


                            }
                        }
                        else {
                            if(player == 2 ){
                                Log.e("test", status + "  For SERVER message");
                                int s = fromServer.readInt();
                                status = s;
                                Log.e("test", status + "  For SERVER message");
                                if(status == -1){
                                    playerOn = false;
                                }
                                if(status == 1){


                                    //create socket connection with player1
                                    int playerPort = 5534;

                                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                    String newHost = input.readLine();
                                    Log.e("Ip of player", newHost+ "  received");

                                    Socket playerSocket = new Socket(newHost, playerPort);
                                    DataInputStream fromPlayer1 = new DataInputStream(playerSocket.getInputStream());
                                    DataOutputStream toPlayer1 = new DataOutputStream(playerSocket.getOutputStream());

                                    //receive image
                                    byte[] data;//String read = input.readLine();
                                    int len= fromPlayer1.readInt();
                                    data = new byte[len];
                                    if (len > 0) {
                                        fromPlayer1.readFully(data, 0, data.length);
                                    }
                                    Bitmap bitmapImg = BitmapFactory.decodeByteArray( data, 0, len);

                                    Constants.bitmap = bitmapImg;
                                    Log.e("image", len + "  received");
                                    //continue to game
                                    Intent intent = new Intent(getApplicationContext(), AndrongActivity.class);
                                    Constants.setSocket(socket);
                                    intent.fillIn(receivedIntent,Intent.FILL_IN_DATA);

                                    toPlayer1.writeInt(1);
                                    Log.e("22222222222222s", 1 + "started");
                                    playerOn = false;
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getApplicationContext().startActivity(intent);
                                    finish();


                                }

                            }

                        }

                    }


                    //toServer.writeInt(1111);
                }
            } catch (IOException e) {
                Log.e("Error", "IO Exception.", e);
            }


        }


    }
    public synchronized void startGame(){
        Intent intent = new Intent(getApplicationContext(), AndrongActivity.class);
        intent.fillIn(receivedIntent,Intent.FILL_IN_DATA);
        getApplicationContext().startActivity(intent);
    }

}
