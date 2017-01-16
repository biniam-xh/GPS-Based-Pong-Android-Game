package com.biniam.android.gpsbasedpongandroidgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class AndrongSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
   private AndrongThread androngThread;
   private TextView statusText;
   private SurfaceHolder holder;
   private Context context;
   public static TextView scoreText;
   public static TextView speedText;
   public static TextView liveText;
   public LinearLayout finalScoreBoard;
   public AndrongSurfaceView(Context context, AttributeSet attrs)
   {
      super(context, attrs);
      this.context = context;
      this.holder = getHolder();
      holder.addCallback(this);
      setFocusable(true);
      androngThread = CreateNewAndrongThread();
   }

   public void setTextView(TextView textView)
   {
      statusText = textView;
   }
   public void setScoreText(TextView v){scoreText = v;}
   public void setLiveText(TextView v){liveText = v;}
   public void setLinear(LinearLayout l){finalScoreBoard = l;}

   public void surfaceCreated(SurfaceHolder surfaceHolder)
   {
      androngThread.setRunning(true);
      try
      {
         androngThread.start();
      }
      catch (Exception error)
      {
         androngThread = CreateNewAndrongThread();
         androngThread.start();
         androngThread.setRunning(true);
      }
   }

   private AndrongThread CreateNewAndrongThread()
   {
      Toast toast = Toast.makeText(context, "Select Menu for a new game", Toast.LENGTH_LONG);
      toast.show();

      return new AndrongThread(holder, context, new Handler()
      {
         @Override
         public void handleMessage(Message m)
         {
            Bundle bundle = m.getData();
            if (bundle.containsKey("viz"))
            {
             //  statusText.setVisibility(VISIBLE);
               scoreText.setText(m.getData().getString("text"));
               liveText.setText(m.getData().getString("live"));
            }
            else if (bundle.containsKey("toast"))
            {
//               Toast toast = Toast.makeText(context, bundle.getString("toast"), Toast.LENGTH_LONG);
//               toast.show();
               Intent intent = new Intent(context, SeventhActivity.class);
               context.startActivity(intent);
               //finalScoreBoard.setVisibility(VISIBLE);
            }
         }
      });
   }

   public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height)
   {
      androngThread.setSurfaceSize(width, height);
   }

   public void surfaceDestroyed(SurfaceHolder surfaceHolder)
   {
      androngThread.setRunning(false);
      boolean retry = true;
      while (retry)
      {
         try
         {
            androngThread.join();
            retry = false;
         }
         catch (InterruptedException e)
         {
         }
      }
   }

   public AndrongThread getAndroidPongThread()
   {
      return androngThread;
   }

   @Override
   public void onWindowFocusChanged(boolean hasWindowFocus)
   {
      if (!hasWindowFocus)
      {
         androngThread.pause();
      }
   }

   @Override
   public boolean onTouchEvent(MotionEvent event)
   {
//      float xPosition1 = 0;
//      float yPosition1 = 0;
//      float xPosition2 = 0;
//      float yPosition2 = 0;
//
//      for (int pointerIndex = 0; pointerIndex < event.getPointerCount(); pointerIndex++)
//      {
//         if (pointerIndex == 0)
//         {
//            xPosition1 = event.getX(pointerIndex);
//            yPosition1 = event.getY(pointerIndex);
//         }
//
//         if (pointerIndex == 1)
//         {
//            xPosition2 = event.getX(pointerIndex);
//            yPosition2 = event.getX(pointerIndex);
//         }
//      }
//
//      switch (event.getAction())
//      {
//         case MotionEvent.ACTION_MOVE:
//            androngThread.setBattPosition(xPosition1, yPosition1, xPosition2, yPosition2);
//            break;
//      }
      return true;
   }
}
