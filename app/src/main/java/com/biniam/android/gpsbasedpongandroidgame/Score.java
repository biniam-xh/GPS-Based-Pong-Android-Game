package com.biniam.android.gpsbasedpongandroidgame;

public class Score
{
   public int player1Score;
   public int player2Score;
   public int scoreToWin;

   public Score(int scoreToWin)
   {
      this.scoreToWin = scoreToWin;
   }

   public void Reset()
   {
      player1Score = 0;
      player2Score = 0;
   }
   public int player1Live(){
      return (scoreToWin - player1Score);
   }
   public  int player2Live(){
      return (scoreToWin - player2Score);
   }
   public void Player1Scored()
   {
      player1Score++;
      //AndrongActivity.scoreText.setText(player1Score + " : " + player2Score);
      //AndrongActivity.liveText.setText(3 - player1Score + "");
   }

   public void Player2Scored()
   {
      player2Score++;
      //AndrongActivity.scoreText.setText("Score " + player1Score + " : " + player2Score);
   }

   public String CreateScoreBoard()
   {
      return "Score " + player1Score + " : " + player2Score;
   }

   public String CreateWinnerBoard()
   {

      if (player1Score > player2Score )
            return "Player 1 has won the game";
      else

         return "Player 2 has won the game";

   }

   public boolean isGameFinished()
   {
      if (player1Score >= scoreToWin || player2Score >= scoreToWin)
         return true;
      else
         return false;
   }
}
