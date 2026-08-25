import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * A class that implements the Starbucks abstract class.
 * Provides a simple structure to store Starbucks locations
 * and retrieve the nearest location based on coordinates.
 * 
 * Author: Austin Campbell
 */
public class StudentStarbucks extends Starbucks {
    
    /**
     * Builds a data structure containing all provided Starbucks locations.
     *
     * If two locations have coordinates such that both
     * |x1 - x2| <= 0.00001 and |y1 - y2| <= 0.00001,
     * only one should be added to avoid duplicate entries.
     * Make sure that you are implementing this in your implementation.
     *
     * @param allLocations An array of Locations objects representing all Starbucks
     *                     locations.
     */

    private static final double DUPLICATE_TOLERANCE = .00001;
    private ArrayList<Location> locations;
    private Node root;


    @Override
    public void build(Location[] allLocations) {
        locations = new ArrayList<Location>();

        HashMap<String, ArrayList<Location>> seen =
                new HashMap<String, ArrayList<Location>>();

        for (Location loc : allLocations) {
            if (loc != null && !isDuplicate(loc, seen)) {
                locations.add(new Location(loc));
                addSeen(loc, seen);
            }
        }

        root = buildTree(locations, 0);
        }

    /**
     * Finds and returns a DEEP copy of the Starbucks location nearest
     * to the given longitude and latitude.
     *
     * @param lng The longitude of the query point, in degrees.
     * @param lat The latitude of the query point, in degrees.
     * @return A deep copy of the nearest Locations object, or null if no locations
     *         exist.
     */
    @Override
    public Location getNearest(double lng, double lat, Set<String> services) {
        Best best = new Best();
        nearest(root, lng, lat, services, best);

        if (best.location == null) {
            return null;
        }

        return new Location(best.location);
    }

    /**
     * builds KD tree via recursion with a list of all locations
     * @param list
     * @param depth
     * @return
     */
    private Node buildTree(ArrayList<Location> list, int depth) {
        if (list.isEmpty()) {
            return null;
        }

        int axis = depth % 2;
        bubbleSort(list, axis);

        int mid = list.size() / 2;
        Node node = new Node(list.get(mid), axis);

        node.left = buildTree(new ArrayList<Location>(list.subList(0, mid)),
                depth + 1);

        node.right = buildTree(new ArrayList<Location>(
                list.subList(mid + 1, list.size())), depth + 1);

        return node;
    }
    /**
     * helps sort locations by long or lat
     * @param list
     * @param axis
     */
    private void bubbleSort(ArrayList<Location> list, int axis) {
        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Location a = list.get(j);
                Location b = list.get(j + 1);

                boolean swap;

                if (axis == 0) {
                    swap = a.lng > b.lng;
                } else {
                    swap = a.lat > b.lat;
                }

                if (swap) {
                    list.set(j, b);
                    list.set(j + 1, a);
                }
            }
        }
    }
    /**
     * searches the KD tree for nearest valid location via recursion
     * @param node
     * @param lng
     * @param lat
     * @param services
     * @param best
     */
    private void nearest(Node node, double lng, double lat,
        Set<String> services, Best best) {
        if (node == null) {
            return;
        }

        updateBest(node.location, lng, lat, services, best);

        double diff = axisDiff(node, lng, lat);

        Node first = diff < 0 ? node.left : node.right;
        Node second = diff < 0 ? node.right : node.left;

        nearest(first, lng, lat, services, best);

        if (best.location == null || diff * diff < best.degreeDistance) {
            nearest(second, lng, lat, services, best);
        }
    }
    /**
     * updates the best location if a better one is found
     * @param loc
     * @param lng
     * @param lat
     * @param services
     * @param best
     */
    private void updateBest(Location loc, double lng, double lat,
        Set<String> services, Best best) {

        if (!loc.hasServices(services)) {
            return;
        }

        double actual = Starbucks.distance(loc.lng, loc.lat, lng, lat);
        double degree = degreeDistance(loc, lng, lat);

        if (actual < best.actualDistance) {
            best.location = loc;
            best.actualDistance = actual;
            best.degreeDistance = degree;
        }
    }
    /**
     * checks for duplicate of a location
     * @param loc
     * @param seen
     * @return
     */
    private boolean isDuplicate(Location loc,
        HashMap<String, ArrayList<Location>> seen) {

        int gx = gridIndex(loc.lng);
        int gy = gridIndex(loc.lat);

        for (int x = gx - 1; x <= gx + 1; x++) {

            for (int y = gy - 1; y <= gy + 1; y++) {

                if (hasDuplicateInBucket(loc, seen.get(x + "," + y))) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * checks for duplicate within the bucket
     * @param loc
     * @param bucket
     * @return
     */
    private boolean hasDuplicateInBucket(Location loc,
        ArrayList<Location> bucket) {

        if (bucket == null) {

            return false;
        }

        for (Location other : bucket) {

            if (Math.abs(loc.lng - other.lng) <= DUPLICATE_TOLERANCE
                    && Math.abs(loc.lat - other.lat) <= DUPLICATE_TOLERANCE) {
                return true;
            }
        }

        return false;
    }

    /**
     * adds a location to the duplicate tracking structure
     * @param loc
     * @param seen
     */
    private void addSeen(Location loc,
            HashMap<String, ArrayList<Location>> seen) {

        String key = gridIndex(loc.lng) + "," + gridIndex(loc.lat);

        if (!seen.containsKey(key)) {

            seen.put(key, new ArrayList<Location>());
        }

        seen.get(key).add(loc);
    }
    
    /**
     * calculates the difference along axis
     * @param node
     * @param lng
     * @param lat
     * @return
     */
    private double axisDiff(Node node, double lng, double lat) {
        if (node.axis == 0) {

            return lng - node.location.lng;
        }

        return lat - node.location.lat;
    }
    /**
     * coverts coords to grid index for duplicate checks
     * @param value
     * @return
     */
    private int gridIndex(double value) {

        return (int) Math.floor(value / DUPLICATE_TOLERANCE);
    }
    /**
     * computes squared distance in coordinate space
     * @param loc
     * @param lng
     * @param lat
     * @return
     */
    private double degreeDistance(Location loc, double lng, double lat) {
        double lngX = loc.lng - lng;
        double latY = loc.lat - lat;

        return lngX * lngX + latY * latY;
    }
     private static class Node {
        private Location location;
        private Node left;
        private Node right;
        private int axis;

        /**
         * creates node for KD tree
         *
         * @param location
         * @param axis
         */
        public Node(Location location, int axis) {

            this.location = location;
            this.axis = axis;
        }
    }

    /**
     * stores best search result
     */
    private static class Best {

        private Location location;
        private double actualDistance = Double.MAX_VALUE;
        private double degreeDistance = Double.MAX_VALUE;
    }
}
