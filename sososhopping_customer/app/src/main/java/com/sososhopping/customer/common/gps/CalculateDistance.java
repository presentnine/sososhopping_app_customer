package com.sososhopping.customer.common.gps;

import android.util.Log;

public class CalculateDistance {
    public static int distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        Log.d("위경도", lat1 + " " + lat2 + " " + lon1 + " " + lon2);

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        /*double height = el1 - el2;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);*/

        return (int) Math.round(distance);
    }
}
