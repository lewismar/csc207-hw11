package edu.grinnell.csc207.lewismar.json;

import java.util.ArrayList;
import java.util.LinkedList;

public class JSONObject {

    /**
     * Field
     */

    LinkedList<JSONMember> members = new LinkedList<JSONMember>();
    
    /*
     * Global Variable
     */
    int i = 0; // global variable to traverse the string

    
    /*
     * Constructor
     */
    
    public JSONObject(String str) throws Exception {
	
	str = str.replace(" ", "");
	str = str.replace("\n", "");

	String key = "";
	Object value;
	
	
	while (str.charAt(i) != '}') {
	    
	    // Find the key
	    // move past the first brace or comma
	    i++;
	    key = onString(str);
	    
	    // we're at a colon
	    if (str.charAt(++i) != ':') {
		throw new Exception("invalid input");
	    } // if
	    
	    // Cases for value:
	    value = parseValue(str);
	    
	    members.add(new JSONMember(key, value));
	} // while
    } // JSONObject(String str)
    
    
    /*
     * Helper Methods
     */
      
    /**
     * Parse the value of a pair
     * 
     * @pre i is pointing at the value after ':'
     */
     
    private Object parseValue(String str) throws Exception {
	// Cases for value:

	Object value;

	// We have a string
	if (str.charAt(++i) == '\"') {
	    value = onString(str);
	    // move past end quote
	    i++;
	}

	// We have digits
	else if (Character.isDigit(str.charAt(i))) {
	    String toValue = "";
	    String exp = "";
	    boolean exponent = false;
	    boolean isTenPower = false;
	    while (str.charAt(i) != ',' && str.charAt(i) != '}') {
		if (str.charAt(i) == 'e') {
		    exponent = true;
		    // move past a positive sign after an e
		    if (str.charAt(i + 1) == '+') {
			i++;
		    } // if
		} else if (str.charAt(i) == 'E') {
		    exponent = true;
		    isTenPower = true;
		    // move past a positive sign after an E
		    if (str.charAt(i + 1) == '+') {
			i++;
		    } // if
		} else if (exponent) {
		    exp += str.charAt(i);
		} else {
		    toValue += str.charAt(i);
		} // else

		i++;
	    } // while (not comma)

	    if (exponent) {
		if (isTenPower) {
		    value = new Double(toValue)
			    * Math.pow(10, (new Integer(exp)));
		} // if (isTenPower)
		else {
		    value = Math.pow(new Double(toValue), new Integer(exp));
		} // else
	    } else {
		value = new Double(toValue);
	    } // else
	} // else if (digit)

	// We have another object
	else if (str.charAt(i) == '{') {
	    value = new JSONObject(inBrace(str));
	    // move past the end brace of the object
	    i++;
	} // else if

	// we have an array
	else if (str.charAt(i) == '[') {
	    ArrayList<Object> vals = new ArrayList<Object>();

	    // if it is an empty array move past the value and return the empty
	    // array
	    if (str.charAt(i + 1) == ']') {
		i += 2;
		return vals;
	    } // if

	    // populate the list from the values in the array
	    while (str.charAt(i) != ']') {
		// move past open brace or comma
		vals.add(parseValue(str));
	    } // while
	      // move past end brace;
	    i++;
	    value = vals;
	} // else if

	else if (str.charAt(i) == 't') {
	    value = true;
	    // advance past the true
	    i += 4;
	} // else if

	else if (str.charAt(i) == 'f') {
	    value = false;
	    // advance past the false
	    i += 5;
	} // else if

	else if (str.charAt(i) == 'n') {
	    value = null;
	    // advance past the null
	    i += 4;
	} // else if

	// otherwise fail
	else {
	    throw new Exception("invalid input");
	} // else

	return value;
    }
    
    /**
     * @pre i is at the position of a quotation mark.
     * @return tmp, the string that is inbetween the quote that i is on and the
     *     occurence of '\"'
     */
    private String onString(String str) {
	// String to return
	String tmp = "";
	// move past quote
	i++; 
	// i is currently at the quote
	while (str.charAt(i) != '\"' && str.charAt(i - 1) != '\\') {
	    tmp += str.charAt(i);
	    i++;
	}
	return tmp;
    } // onString(String str)
    
    /**
     * takes a string that begins with { and returns the substring between that
     * brace and its counterpart
     */
    private String inBrace(String str) {
	int initial = i;
	int open = 1;
	char c;
	// currently at a brace
	i++;
	while (open > 0) {
	    c = str.charAt(++i);
	    if (c == '{') {
		open++;
	    } else if (c == '}') {
		open--;
	    } // if/else if
	} // while
	return str.substring(initial, i + 1);
    } // inBrace(String)

}
