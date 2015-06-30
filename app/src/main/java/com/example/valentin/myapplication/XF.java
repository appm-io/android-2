package com.example.valentin.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by valentin on 6/30/2015.
 */
public class XF {
    public static String getURL(String urlx) throws Exception {
        URL url = new URL(urlx);
        InputStream is = url.openStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line = null;
        String out = "";
        while ((line = br.readLine()) != null) {
            Log.d("getURL", line);
            out += line;
        }
        return out;
    }
}
