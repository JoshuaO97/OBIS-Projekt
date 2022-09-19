package de.th.obis.restapi;

import de.th.obis.restapi.point.Point;
import java.util.ArrayList;

public class Route {

    private final int id;
    private static int nextID = 1;
    private final String name;
    private final double length;
    private final int ascent;
    private final int descent;
    private final int numCaches;
    private final ArrayList<Point> caches;


    public Route(String name, double length, int ascent, int descent, ArrayList<Point> caches) {
        this.id = nextID++;
        this.name = name;
        this.length = length;
        this.ascent = ascent;
        this.descent = descent;
        this.numCaches = caches.size();
        this.caches = caches;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLength() {
        return length;
    }

    public double getAscent() {
        return ascent;
    }

    public double getDescent() {
        return descent;
    }

    public int getNumCaches() {
        return numCaches;
    }

    public ArrayList<Point> getCaches() {
        return caches;
    }
}
