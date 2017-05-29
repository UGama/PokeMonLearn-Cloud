package com.example.pokemonlearn;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Gama on 25/5/17.
 */

public class MusicServer extends Service {
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.background);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Log.i("Media", "Start");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
}
