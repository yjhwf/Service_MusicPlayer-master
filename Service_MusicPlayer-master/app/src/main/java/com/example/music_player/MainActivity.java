package com.example.music_player;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView playbtn;
    String audioLink = "https://drive.google.com/uc?export=download&id=abcdefghijklm";
    boolean musicPlaying = false;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        playbtn = (ImageView) findViewById(R.id.playbtn);
        playbtn.setBackgroundResource(R.drawable.play);
        serviceIntent = new Intent(this, MyPlayService.class);

        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!musicPlaying)
                {
                    playAudio();
                    playbtn.setImageResource(R.drawable.stop);
                    musicPlaying = true;
                }
                else
                {
                    stopPlayService();
                    playbtn.setImageResource(R.drawable.play);
                    musicPlaying = false;
                }

            }
        });
    }

    private void stopPlayService() {
        try
        {
            stopService(serviceIntent);
        }
        catch (SecurityException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio() {
        serviceIntent.putExtra("AudioLink", audioLink);

        try
        {
            startService(serviceIntent);
        }
        catch (SecurityException e)
        {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}