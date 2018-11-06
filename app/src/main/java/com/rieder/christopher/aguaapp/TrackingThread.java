package com.rieder.christopher.aguaapp;

import android.net.Uri;
import android.util.Log;

import org.ankit.gpslibrary.MyTracker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class TrackingThread extends Thread {

    private double latitude;
    private double longitude;
    private Uri coordinates;
    private File file;
    private VentaActivity ctx;

    public TrackingThread(File file, VentaActivity ctx) {
        this.file = file;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        getLocation();
    }

    public Uri getGoogleMapCoordinatesIntent() {
        //se deberia chequear que este inicializado??? TODO
        return coordinates;
    }

    public String getLocationText() {
        return latitude + "," + longitude;
    }

    private void getLocation() {
        MyTracker tracker = new MyTracker(ctx);
        latitude = tracker.getLatitude();
        longitude = tracker.getLongitude();

        coordinates = Uri.parse("geo:" + latitude + "," + longitude);
        Log.v("COORDINATES", latitude + "," + longitude);

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(file, true), 1024);
            out.write(latitude + "," + longitude);
            out.newLine();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
