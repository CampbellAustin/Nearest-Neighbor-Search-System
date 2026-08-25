import java.util.HashSet;
import java.util.Set;

/**
 * Note : DO NOT CHANGE THIS FILE!!!
 * A Data Type class that holds the necessary details of a Starbucks location,
 * including city, address, longitude, latitude, and available services.
 * 
 * Author: amjadm
 */
public class Location {

    /** The city where the Starbucks location is found. */
    public String city;

    /** The address of the Starbucks location. */
    public String address;

    /** The longitude coordinate of the Starbucks location. */
    public double lng;

    /** The latitude coordinate of the Starbucks location. */
    public double lat;

    /** The services available at this Starbucks location. */
    public Set<String> services;

    /**
     * Default constructor.
     * Initializes a location with empty city and address,
     * longitude and latitude set to 0.0, and an empty services set.
     */
    public Location() {
        this.city = "";
        this.address = "";
        this.lng = 0.0;
        this.lat = 0.0;
        this.services = new HashSet<String>();
    }

    /**
     * Parameterized constructor.
     * Initializes a location with specified city, address, longitude,
     * latitude, and services.
     *
     * @param icity     The city name.
     * @param iaddress  The address.
     * @param ilng      The longitude coordinate.
     * @param ilat      The latitude coordinate.
     * @param iservices The services as a semicolon separated string.
     */
    public Location(String icity, String iaddress, double ilng, double ilat, String iservices) {
        this.city = icity;
        this.address = iaddress;
        this.lng = ilng;
        this.lat = ilat;
        this.services = parseServices(iservices);
    }

    /**
     * Copy constructor.
     * Creates a new Locations object that is a deep copy of another Locations
     * object.
     *
     * @param orig The original Locations object to copy.
     */
    public Location(Location orig) {
        this.city = orig.city;
        this.address = orig.address;
        this.lng = orig.lng;
        this.lat = orig.lat;
        this.services = new HashSet<String>(orig.services);
    }

    /**
     * Checks whether this Starbucks location offers all requested services.
     * 
     * @param requestedServices the services to search for
     * @return true if this location offers every requested service, false otherwise
     * @throws IllegalArgumentException if requestedServices is null or empty
     */

    public boolean hasServices(Set<String> requestedServices) {
        if (requestedServices == null || requestedServices.isEmpty()) {
            throw new IllegalArgumentException("The provided services set is null or empty!");
        }
        return this.services.containsAll(requestedServices);
    }

    /**
     * Converts a semicolon separated services string into a Set.
     * For example, "drive thru;cold brew;breakfast" becomes a set
     * containing "drive thru", "cold brew", and "breakfast".
     *
     * @param iservices the semicolon separated services string from the CSV file
     * @return a Set containing the individual services in lowercase
     * @throws NullPointerException if iservices is null or empty
     */
    private static Set<String> parseServices(String iservices) {
        Set<String> result = new HashSet<String>();

        if (iservices == null || iservices.length() == 0) {
            throw new NullPointerException("The Provided service is null or invalid!");
        }

        String[] parts = iservices.split(";");

        for (int i = 0; i < parts.length; i++) {
            result.add(parts[i].trim().toLowerCase());
        }

        return result;
    }
}