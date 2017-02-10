package model;
import com.sun.jersey.api.client.GenericType;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by edwin on 2/1/17.
 */
public class Coordinates {

    final double longitude;
    final double latitude;

    public Coordinates(
        @JsonProperty("longitude") double longitude,
        @JsonProperty("latitude") double latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }
}
