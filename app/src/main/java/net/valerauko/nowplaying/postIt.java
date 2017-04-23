package net.valerauko.nowplaying;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by valerauko on 17/04/23.
 */

public class postIt extends AsyncTask<String, Void, String> {

    private String tConsumerKey = "***";
    private String tNonce = "***";
    private String tUserToken = "***";
    private String tUserSecret = "***";

    private String mClientToken = "***";
    private String mClientSecret = "***";
    private String mUserToken = "***";
    private String mUrl = "instance url";

    public int result = 0;

    protected String doInBackground(String... msg) {

        Log.i("doInBackground","Attempting");
        String mesg = msg[0];
        try {
            mastodon(mesg);
            twitter(mesg);
        } catch(Exception e){
            Log.i("Error", e.toString());
            result = 401;
        }
        result = 200;

        return "OK";
    }
    private void twitter(String msg) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(tConsumerKey)
                .setOAuthConsumerSecret(tNonce)
                .setOAuthAccessToken(tUserToken)
                .setOAuthAccessTokenSecret(tUserSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Log.i("Twitter",twitter.updateStatus(msg).toString());
    }

    private void mastodon(String msg) {
        HttpURLConnection client = null;
        try {
            String data = "client_id=" + mClientToken + "&" +
                    "client_secret=" + mClientSecret + "&" +
                    "visibility=public&sensitive=false&status=" +
                    URLEncoder.encode(msg, "UTF-8");

            URL url = new URL(mUrl);
            client = (HttpURLConnection) url.openConnection();

            client.setRequestMethod("POST");
            client.setRequestProperty("Authorization", "Bearer " + mUserToken);
            client.setDoOutput(true);

            OutputStreamWriter outputPost = new OutputStreamWriter(client.getOutputStream());
            outputPost.write(data);
            outputPost.flush();
            outputPost.close();

            int statusCode = client.getResponseCode();
            Log.i("Result", String.valueOf(statusCode));

        } catch (Exception error) {
            Log.i("Error", error.toString());
        } finally {
            if (client != null)
                client.disconnect();
        }
    }
}
