package com.example.valentin.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class Stats extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Handler handler = new Handler();
        CustomTimerTask task = new CustomTimerTask(handler);
        new Timer().scheduleAtFixedRate(task, 0, 1000);
    }

    public class CustomTimerTask extends TimerTask {
        private Handler handler;

        public CustomTimerTask(Handler h) {
            this.handler = h;
        }

        @Override
        public void run() {
            getData();
            handler.post(new Runnable() {
                public void run() {
                    //update UI here.
                }
            });
        }
    }

    public void getData() {
        Log.d("WEB", "Start");

        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void... params) {
                Log.d("WEB", "Init");
                try {
                    return XF.getURL("http://d4.xfactorapp.com/nginx_status");
                } catch (Exception e) {
                    Log.d("WEB", "Nu a mers");
                }
               /*
                try {
                    return XF.getURL("http://d3.xfactorapp.com/creative/sys/android1");
                } catch (Exception e) {
                    Log.d("WEB", "Nu a mers");
                }*/
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("WEB", "onPostExecute");
                int length = result.length();
                int pos = result.indexOf("Waiting:");
                pos += 9;
                int diff = length - pos;

                String con = result.substring(20, result.indexOf("server"));
                String waiting = "" + result.substring(pos, length);
                Log.d("CUCU", waiting);
                final TextView load_stats = (TextView) findViewById(R.id.load_stats);
                load_stats.setText(con);
                final TextView cpu_stats = (TextView) findViewById(R.id.cpu_stats);
                cpu_stats.setText(waiting);
                /*
                try {
                    JSONObject item = (JSONObject) (new JSONTokener(result).nextValue());

                    final TextView cpu_stats = (TextView) findViewById(R.id.cpu_stats);
                    cpu_stats.setText(item.getString("cpu"));

                    final TextView ram_stats = (TextView) findViewById(R.id.ram_stats);
                    ram_stats.setText(item.getString("ram"));

                    final TextView load_stats = (TextView) findViewById(R.id.load_stats);
                    load_stats.setText(item.getString("load"));

                    final TextView net_stats = (TextView) findViewById(R.id.net_stats);
                    net_stats.setText(item.getString("net"));

                    final TextView disk_stats = (TextView) findViewById(R.id.disk_stats);
                    disk_stats.setText(item.getString("disk"));

                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
