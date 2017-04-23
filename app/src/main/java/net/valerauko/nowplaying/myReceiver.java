package net.valerauko.nowplaying;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by valerauko on 17/04/23.
 */

public class myReceiver extends BroadcastReceiver {

    public String artist, album, track;
    public Boolean caught = false;

    public myReceiver(){}

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!caught) caught = true;

        artist = intent.getStringExtra("artist");
        album = intent.getStringExtra("album");
        track = intent.getStringExtra("track");

    }
}