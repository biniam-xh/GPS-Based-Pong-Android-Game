package com.biniam.android.gpsbasedpongandroidgame;

import android.graphics.Bitmap;

import java.net.Socket;

/**
 * Created by USER on 1/18/2017.
 */

public class Constants {
    public static Bitmap bitmap;
    public static Socket socket;
    public static boolean gameOver = false;

    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(Socket socket){
        Constants.socket = socket;
    }
}
