package com.example.sahmed.utilityapp.utility;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by mehab on 10/19/2017.
 */

public class LocationUtils {


    private static final String TAG = "LocationUtils";
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    private static OnLocationChangeListener mListener;
    private static MyLocationListener myLocationListener;
    private static LocationManager mLocationManager;

    private LocationUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }



    /**
     * Determine if Gps is available
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getApplicationContext().getSystemService(LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Determine if positioning is available
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLocationEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getApplicationContext().getSystemService(LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Open the Gps settings screen
     */
    public static void openGpsSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.getApplicationContext().startActivity(intent);
    }

    /**
     * registered
     * <p>Use the remember call{@link #unregister()}</p>
     * <p>Need to add permission {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>}</p>
     * <p>Need to add permission {@code <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>}</p>
     * <p>in case{@code minDistance} is 0，Then pass{@code minTime}To update regularly；</p>
     * <p>{@code minDistance}Not 0，则以{@code minDistance}Prevail；</p>
     * <p>Both are 0，Then refresh at any time。</p>
     *
     * @param minTime     Location information update cycle（unit：millisecond）
     * @param minDistance Position change minimum distance：When the position distance changes beyond this value，The location information will be updated（unit：Meter）
     * @param listener    Location Refresh the callback interface
     * @return {@code true}: Initialized successfully<br>{@code false}: initialization failed
     */
    public static boolean register(long minTime, long minDistance, OnLocationChangeListener listener, Context context) {
        if (listener == null) return false;
        mLocationManager = (LocationManager) context.getApplicationContext().getSystemService(LOCATION_SERVICE);
        mListener = listener;
        if (!isLocationEnabled(context) ){
            Log.d(TAG, "无法定位，请打开定位服务");
            return false;
        }
        String provider = mLocationManager.getBestProvider(getCriteria(), true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);
        if (location != null) listener.getLastKnownLocation(location);
        if (myLocationListener == null) myLocationListener = new MyLocationListener();
        mLocationManager.requestLocationUpdates(provider, minTime, minDistance, myLocationListener);
        return true;
    }


    /**
     * Write off
     */
    public static void unregister() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager.removeUpdates(myLocationListener);
                myLocationListener = null;
            }
            mLocationManager = null;
        }
    }

    /**
     * Set the positioning parameters
     *
     * @return {@link Criteria}
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // Set the positioning accuracy Criteria.ACCURACY_COARSE more rough，Criteria.ACCURACY_COARSE more rough
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // Set whether or not to require speed
        criteria.setSpeedRequired(false);
        // Set whether to allow operator charges
        criteria.setCostAllowed(false);
        // Set whether or not azimuth information is required
        criteria.setBearingRequired(false);
        // Set whether altitude information is required
        criteria.setAltitudeRequired(false);
        // Set the demand for power
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * Get the geographic location based on latitude and longitude
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return {@link Address}
     */
    public static Address getAddress(double latitude, double longitude,Context context) {
        Geocoder geocoder = new Geocoder(context.getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) return addresses.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * According to latitude and longitude to obtain the country
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return Country
     */
    public static String getCountryName(double latitude, double longitude,Context context) {
        Address address = getAddress(latitude, longitude,context);
        return address == null ? "unknown" : address.getCountryName();
    }

    /**
     * According to latitude and longitude to obtain the location
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return location
     */
    public static String getLocality(double latitude, double longitude,Context context) {
        Address address = getAddress(latitude, longitude,context);
        return address == null ? "unknown" : address.getLocality();
    }

    /**
     * According to latitude and longitude to get the street
     *
     * @param latitude  latitude
     * @param longitude longitude
     * @return Where the street
     */
    public static String getStreet(double latitude, double longitude,Context context) {
        Address address = getAddress(latitude, longitude,context);
        return address == null ? "unknown" : address.getAddressLine(0);
    }

    /**
     * Whether it is better position
     *
     * @param newLocation         The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     * @return {@code true}: yes <br>{@code false}: no
     */
    public static boolean isBetterLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(newLocation.getProvider(), currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Whether the same provider
     *
     * @param provider0 Providers 1
     * @param provider1 Provider 2
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isSameProvider(String provider0, String provider1) {
        if (provider0 == null) {
            return provider1 == null;
        }
        return provider0.equals(provider1);
    }

    private static class MyLocationListener
            implements LocationListener {
        /**
         * This function is fired when the coordinates change, and if the Provider passes in the same coordinates, it will not be triggered
         *
         * @param location coordinate
         */
        @Override
        public void onLocationChanged(Location location) {
            if (mListener != null) {
                mListener.onLocationChanged(location);
            }
        }

        /**
         * The provider triggers this function when it is switched between three states that are available, temporarily unavailable, and no service
         *
         * @param provider provider
         * @param status   status
         * @param extras   provider optional package
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (mListener != null) {
                mListener.onStatusChanged(provider, status, extras);
            }
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d(TAG, "当前GPS状态为可见状态");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d(TAG, "当前GPS状态为服务区外状态");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d(TAG, "当前GPS状态为暂停服务状态");
                    break;
            }
        }

        /**
         * This function is triggered when enable is enabled, such as GPS is turned on
         */
        @Override
        public void onProviderEnabled(String provider) {
        }

        /**
         * This function is triggered when provider is disabled, such as when GPS is turned off
         */
        @Override
        public void onProviderDisabled(String provider) {
        }
    }

    public interface OnLocationChangeListener {

        /**
         * Gets the last reserved coordinates
         *
         * @param location coordinate
         */
        void getLastKnownLocation(Location location);

        /**
         * This function is fired when the coordinates change, and if the Provider passes in the same coordinates, it will not be triggered
         *
         * @param location coordinate
         */
        void onLocationChanged(Location location);

        /**
         * The provider triggers this function when it is switched between three states that are available, temporarily unavailable, and no service
         *
         * @param provider provider
         * @param status   status
         * @param extras   provider optional package
         */
        void onStatusChanged(String provider, int status, Bundle extras);//The position status has changed
    }

}
