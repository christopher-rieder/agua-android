package com.rieder.christopher.aguaapp;

import android.net.Uri;
import android.util.Log;

import org.ankit.gpslibrary.MyTracker;

public class TrackingThread extends Thread {

    private double latitude;
    private double longitude;
    private Uri coordinates;
    private VentaActivity ctx;

    public TrackingThread(VentaActivity ctx) {
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
    }
}
