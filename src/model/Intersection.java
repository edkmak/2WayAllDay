package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwin on 2/2/17.
 */
public class Intersection {

    final String name;
    final String street;
    final String avenue;
    final List<StopSign> stops;

    public Intersection(String street, String avenue, ) {
        this.name = street.charAt(0) + "-" + avenue.substring(0,2); //M-46
        this.street = street;
        this.avenue = avenue;
        this.stops = new ArrayList<StopSign>;
    }

    //To-Do 1)Decide implementation of the graph before finishing htis clas

    public String getStreet() {
        return street;
    }

    public String getAvenue() {
        return avenue;
    }

    public int getStops() {
        return stops;
    }
}
