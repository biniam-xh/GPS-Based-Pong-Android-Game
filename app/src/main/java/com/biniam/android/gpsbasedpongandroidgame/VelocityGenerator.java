package com.biniam.android.gpsbasedpongandroidgame;

import java.util.Random;

public class VelocityGenerator
{
   public static  int MaxSpeed = 300;
   public static  int MinimumYSpeed = 100;
   public static  int MinimumXSpeed = 100;
   private Random speedRandomizer;

   public VelocityGenerator()
   {
      speedRandomizer = new Random(50);
   }

   public Velocity GenerateInitialVelocity()
   {
      return new Velocity(162.0, -460.0);
   }

   public Velocity GenerateNewReverseDown(Velocity velocity)
   {
      int xVelocity = GenerateNewXSpeed();
      int yVelocity = GenerateNewYSpeed();

      if (velocity.xVelocity > 0)
         xVelocity = -xVelocity;

      return new Velocity(xVelocity, yVelocity);
   }

   public Velocity GenerateNewReverseUp(Velocity velocity)
   {
      int xVelocity = GenerateNewXSpeed();
      int yVelocity = GenerateNewYSpeed();

      if (velocity.xVelocity > 0)
         xVelocity = -xVelocity;

      return new Velocity(xVelocity, -yVelocity);
   }

   private int GenerateNewYSpeed()
   {
      return speedRandomizer.nextInt(MaxSpeed) + MinimumYSpeed;
   }

   private int GenerateNewXSpeed()
   {
      return speedRandomizer.nextInt(MaxSpeed) + MinimumXSpeed;
   }
}
