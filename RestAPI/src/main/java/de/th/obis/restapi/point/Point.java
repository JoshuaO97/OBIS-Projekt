package de.th.obis.restapi.point;

public class Point {

    private double latitude;
    private double longitude;
    private int code;

    public Point(double latitude, double longitude, int code) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.code = code;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
