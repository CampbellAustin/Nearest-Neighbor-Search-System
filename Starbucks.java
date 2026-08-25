import java.util.Set;

/**
 * Note : DO NOT CHANGE THIS FILE!!!
 * An abstract class that needs to be implemented by
 * StudentStrabucks class.
 * 
 * @author amjadm
 */
public abstract class Starbucks {

	/**
	 * Calculates the distance between two points on Earth based on their longitude
	 * and latitude.
	 * 
	 * @param long1 The longitude of the first point, in degrees.
	 * @param lat1  The latitude of the first point, in degrees.
	 * @param long2 The longitude of the second point, in degrees.
	 * @param lat2  The latitude of the second point, in degrees.
	 * @return The distance between the two points in kilometers (km).
	 */
	public static double distance(double long1, double lat1, double long2, double lat2) {
		double R = 6371; // Radius of Earth in kilometers
		double dLat = (lat2 - lat1) * Math.PI / 180;
		double dLon = (long2 - long1) * Math.PI / 180;
		lat1 = lat1 * Math.PI / 180;
		lat2 = lat2 * Math.PI / 180;

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c;

		return d;
	}

	/**
	 * Builds a data structure containing all provided Starbucks locations.
	 * 
	 * If two locations have coordinates such that both
	 * |x1 - x2| <= 0.00001 and |y1 - y2| <= 0.00001,
	 * only one should be added to avoid duplicate entries.
	 * Make sure that you are implementing this in your implementation.
	 * 
	 * @param allLocations An array of Locations objects representing Starbucks
	 *                     locations.
	 */
	public abstract void build(Location[] allLocations);

	/**
	 * Finds and returns a deep copy of the nearest Starbucks location that offers
	 * all of the requested services.
	 *
	 * The search is based on the given longitude and latitude coordinates.
	 * Locations
	 * that do not offer every requested service are ignored.
	 *
	 * @param lng      the longitude of the query point, in degrees
	 * @param lat      the latitude of the query point, in degrees
	 * @param services the required services, such as "drive thru", "24/7",
	 *                 "cold brew", or "breakfast"
	 * @return a deep copy of the nearest Locations object that offers all requested
	 *         services, or null if no matching location is found
	 */
	public abstract Location getNearest(double lng, double lat, Set<String> services);
}
