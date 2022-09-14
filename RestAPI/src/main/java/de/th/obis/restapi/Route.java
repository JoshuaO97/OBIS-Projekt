package de.th.obis.restapi;

import de.th.obis.restapi.point.Point;
import java.util.ArrayList;

public class Route {

    private final int id;
    private static int nextID = 1;
    private String name;
    private final double length;
    private final double altitude;
    private final int numCaches;
    private final ArrayList<Point> caches;


    public Route(String name, double length, double altitude, ArrayList<Point> caches) {
        this.id = nextID++;
        this.name = name;
        this.length = length;
        this.altitude = altitude;
        this.numCaches = caches.size();
        this.caches = caches;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public double getAltitude() {
        return altitude;
    }

    public int getNumCaches() {
        return numCaches;
    }

    public ArrayList<Point> getCaches() {
        return caches;
    }
}
