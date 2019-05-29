// Copyright 2016 Diego Martos Buoro
// This file is part of QASP.
//
// QASP is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// QASP is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with QASP.  If not, see <http://www.gnu.org/licenses/>.
package com.qasp.diego.arsp;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class LocalizacaoGPSService extends Service {

    final int INTERVALO_TEMPO = 15000;
    final int INTERVALO_DISTANCIA = 10;
    LocationManager lm;
    LocationListener ll;
    Location ultimamedicaoGPS;
    double Lat = 0;
    double Long = 0;

    class MeuLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location locFromGps) {
            // called when the listener is notified with a location update from the GPS
            Lat = locFromGps.getLatitude();
            Long = locFromGps.getLongitude();
            Global.GPS.setLatitude(Lat);
            Global.GPS.setLongitude(Long);
            Global.GPSok = true;
            Log.i("Geo_Location", "Latitude: " + String.valueOf(getLat()) + ", Longitude: " + String.valueOf(getLong()));
        }

        @Override
        public void onProviderDisabled(String provider) {
            // called when the GPS provider is turned off (user turning off the GPS on the phone)
        }

        @Override
        public void onProviderEnabled(String provider) {
            // called when the GPS provider is turned on (user turning on the GPS on the phone)
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // called when the status of the GPS provider changes
        }
    }

    @Override
    public void onCreate(){
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new MeuLocationListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(checkPermission(getApplicationContext())) lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, INTERVALO_TEMPO, INTERVALO_DISTANCIA, ll);
        ultimamedicaoGPS = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Long = ultimamedicaoGPS.getLongitude();
        Lat = ultimamedicaoGPS.getLatitude();
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        if(checkPermission(getApplicationContext())) lm.removeUpdates(ll);
        super.onDestroy();
    }

    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private double getLat() { return Lat; }
    private double getLong() { return Long; }
}