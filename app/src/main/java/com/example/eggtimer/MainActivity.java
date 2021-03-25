package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  int max= 300;
  int millisecondsinfuture;
    long minutes;
    TextView showtime;
  CountDownTimer countDownTimer;
  boolean buttonPressed= false;
  MediaPlayer mediaPlayer;
  AudioManager  audioManager;
  SeekBar seekBar;
  Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
        mediaPlayer= MediaPlayer.create(this,R.raw.horn);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(max);
        seekBar.setProgress(40);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           @Override
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               if (fromUser) {
                   int time = progress * 1000;
                   millisecondsinfuture=time;
                   showtime.setText(updateTimer(millisecondsinfuture));

               }
           }

           @Override
           public void onStartTrackingTouch(SeekBar seekBar) {

           }

           @Override
           public void onStopTrackingTouch(SeekBar seekBar) {

           }
       });
          showtime=(TextView)findViewById(R.id.textView5);

    }
    public String updateTimer(long milliseconds){
        long minutes = (milliseconds / 1000) / 60;
        long secManager=(milliseconds/1000)-(60*minutes);
        String StringsecManager= String.valueOf(secManager);
        if(secManager==60){minutes=0;}
        else if(secManager<=9){StringsecManager="0"+StringsecManager;}
        String time=(String.valueOf(minutes)+":"+StringsecManager);
        return  time;
    }
    public void resetTimer(){
        countDownTimer.cancel();
        go.setText("GO");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        showtime.setText("0"+ ":"+ "30");
    }
    public void go(View view){
         go=(Button)findViewById(R.id.button);
        Log.i("yo", "button pressed");
        if(!buttonPressed) {
            countDownTimer = new CountDownTimer(millisecondsinfuture, 1000) {
                    public void onTick(long millisecondsuntilldone) {
                        showtime.setText(updateTimer(millisecondsuntilldone));
                    }
                public void onFinish() {
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
            go.setText("STOP");
            seekBar.setEnabled(false);
            buttonPressed=true;
        }else{
            resetTimer();
            buttonPressed=false;
        }

    }
}