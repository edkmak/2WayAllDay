package model;
import com.sun.jersey.api.client.GenericType;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;


/**
 * Created by edwin on 2/1/17.
 * This java class represents a single stop sign in the Outer Sunset in San Francisco.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class StopSign {

    public static final GenericType<List<StopSign>> LIST_TYPE = new GenericType<List<StopSign>>() {};

    final int object_id;
    final String street;
    final String cross_street;
    final String direction;
    final String facing_street;
    final Coordinates geom;

    @JsonCreator
    public StopSign(@JsonProperty("object_id") int object_id,
                      @JsonProperty("street") String street,
                      @JsonProperty("cross_street") String cross_street,
                      @JsonProperty("direction") String direction,
                      @JsonProperty("facing_street") String facing_street,
                      @JsonProperty("geom") Coordinates geom)

    {
        this.object_id = object_id;
        this.street = street;
        this.cross_street = cross_street;
        this.direction = direction;
        this.facing_street = facing_street;
        this.geom = geom;
    }

    public int getObjectID()
    {
        return object_id;
    }

    public String getStreet(){
        return street;
    }

    public String getCrossStreet(){
        return cross_street;
    }

    public String getDirection(){
        return direction;
    }

    public String getFacingStreet(){
        return facing_street;
    }

    public Coordinates getGeom(){
        return geom;
    }

    @Override
    public String toString() {
        return "StopSign{" +
                "object_id=" + object_id +
                ", street='" + street + '\'' +
                ", cross_street='" + cross_street + '\'' +
                ", direction='" + direction + '\'' +
                ", facing_street='" + facing_street + '\'' +
                ", geom=" + geom +
                '}';
    }

}
