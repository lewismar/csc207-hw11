package edu.grinnell.csc207.lewismar.json;

/**
 * A JSON member. That is, a key/value pair.
 * @author Earnest Wheeler
 * @author Daniel Goldstein
 * @author Tiffany Nguyen
 * @author Mark Lewis
 *
 */
public class JSONMember {

    String key;
    Object value;
    
    /**
     * Constructor
     * @param k, the key
     * @param v, the value
     */
    public JSONMember(String k, Object v) {
	this.key = k;
	this.value = v;
    } // JSONMember(String, Object)    
} // Class JSONMember
