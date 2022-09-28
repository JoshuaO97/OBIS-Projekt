package de.th.obis.restapi;

import de.th.obis.restapi.point.Point;
import java.util.ArrayList;

public class Route {

    private final int id;
    private final String name;
    private final int length;
    private final int ascent;
    private final int descent;
    private final ArrayList<Point> caches;

    public Route(int id, String name, int length, int ascent, int descent, ArrayList<Point> caches) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.ascent = ascent;
        this.descent = descent;
        this.caches = caches;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public double getAscent() {
        return ascent;
    }

    public double getDescent() {
        return descent;
    }

    public ArrayList<Point> getCaches() {
        return caches;
    }
}
