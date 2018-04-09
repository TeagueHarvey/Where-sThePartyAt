package hu.ait.android.wheresthepartyat.data;

/**
 * Created by teagu_000 on 9/12/2017.
 */

public class MyLatLng {
    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    private double lat;
    private double lng;

    public MyLatLng() {
    }

    public MyLatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

}
