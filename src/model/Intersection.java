package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by edwin on 2/2/17.
 */
public class Intersection implements Comparable<Intersection>{

    private String name; // Letter of street followed by avenue (i.e, M-40)
    private String street;
    private String avenue;
    private List<StopSign> stops;

    public Intersection(String street, String avenue) {
        this.name = street.charAt(0) + "-" + avenue.substring(0,2); //M-46
        this.street = street;
        this.avenue = avenue;
        this.stops = new ArrayList<StopSign>();
    }

    public void add(StopSign stopsign){
        stops.add(stopsign);
    }

    public int getStopCount(){
        return stops.size();
    }

    //To-Do !!1)Decide implementation of the graph before finishing this class

    public String getStreet() {
        return street;
    }

    public String getAvenue() {
        return avenue;
    }

    public List<StopSign> getStops() {
        return stops;
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "name='" + name + '\'' +
                ", type='" + getStopCount() + '\'' +
                ", stops=" + stops.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Intersection)) {
            return false;
        }
        Intersection intersection = (Intersection) o;
        return Objects.equals(name, intersection.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Intersection other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than
        // other and 0 if they are supposed to be equal
        int last = this.street.compareTo(other.street);
        return last == 0 ? this.avenue.compareTo(other.avenue) : last;
    }
}
