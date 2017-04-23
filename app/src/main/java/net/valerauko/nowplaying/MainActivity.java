package net.valerauko.nowplaying;

import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private myReceiver mReceiver = new myReceiver();
    private String message = null;
    private TextView status = null;

    @Override
    protected void onPause() { super.onPause(); }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");
        iF.addAction("com.android.music.playstatechanged");
        iF.addAction("com.android.music.playbackcomplete");
        iF.addAction("com.android.music.queuechanged");

        registerReceiver(mReceiver, iF);

        status = (TextView) findViewById(R.id.response);
    }

    public void sendPost(View view) {
        // Attempt send
        if(!mReceiver.caught) {
            status.setText("Song not yet caught");
            //Log.i("Details", "Song not yet caught");
        } else {
            String artist = mReceiver.artist;
            String album = mReceiver.album;
            String track = mReceiver.track;

            int length = 6 + artist.length() + album.length() + track.length();

            if (length <= 122)
                message = artist + " - " + track + " (" + album + ")";
            else {
                String t_artist = artist.length() > 40 ? artist.substring(0, 38) + "..." : artist;
                String t_album = album.length() > 40 ? album.substring(0, 38) + "..." : album;
                String t_track = track.length() > 40 ? track.substring(0, 38) + "..." : track;

                message = t_artist + " - " + t_track + " (" + t_album + ")";
            }

            String.valueOf(new postIt().execute("#nowplaying "+message));
            status.setText("Last sent:\n"+message);
            //Log.i("Details", message);
        }

    }
}

