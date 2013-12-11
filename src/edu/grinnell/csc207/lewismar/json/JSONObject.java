package edu.grinnell.csc207.lewismar.json;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class that contains a linked list of a JSON Object's members
 * @author Tiffany Nguyen
 * @author Daniel Goldstein
 * @author Mark Lewis
 * @author Earnest Wheeler
 *
 */
public class JSONObject {

    /**
     * Linked list that contains the members of the JSON object
     */
    LinkedList<JSONMember> members = new LinkedList<JSONMember>();
    
    /**
     * Global Variable that keeps track of the character that the 
     * constructor is currently at
     */
    int i = 0; // global variable to traverse the string

    
    /**
     * Constructor that takes a string in JSON formatting and puts
     * the data into a linked list
     */  
    public JSONObject(String str) throws Exception {

	String key = "";
	Object value;
	
	skipWhiteSpace(str);
	
	// add all of the members in the object to members
	while (str.charAt(i) != '}') {
	    
	    // Find the key
	    // move past the first brace or comma and whitespace
	    i++;
	    skipWhiteSpace(str);
	    
	    key = onString(str);
	    
	    // move past end quote and skip any whitespace
	    i++;
	    skipWhiteSpace(str);
	    
	    // we should be at a colon
	    if (str.charAt(i) != ':') {
		throw new Exception("invalid input");
	    } // if
	    
	    // move past colon and skip white space
	    i++;
	    skipWhiteSpace(str);
	    
	    // Cases for value:
	    value = parseValue(str);
	    
	    members.add(new JSONMember(key, value));
	} // while
    } // JSONObject(String str)
    
    
    /*
     * Helper Methods
     */
      
    /**
     * Parse the value of a member
     * 
     * @pre i is pointing at the value after ':'
     */ 
    private Object parseValue(String str) throws Exception {
	// Cases for value:

	Object value; // value to return

	// We have a string
	if (str.charAt(i) == '\"') {
	    value = onString(str);
	    // move past end quote
	    i++;
	} // if

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
		// move past open brace or comma and skip whitespace
		i++;
		skipWhiteSpace(str);
		vals.add(parseValue(str));
		//skip whitespace before next comma/bracket
		skipWhiteSpace(str);
		
	    } // while
	    // move past end brace;
	    i++;
	    skipWhiteSpace(str);
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
    } // parseValue(String)
    
    /**
     * @pre i is at the position of a quotation mark.
     * @return tmp, the string that is in-between the quote that i is on and the
     *     occurrence of '\"'
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
	} // while
	return tmp;
    } // onString(String str)
    
    /**
     * @pre str.charAt(i) == '{'
     * @return the substring that starts with the starting brace and ends with
     *         its counterpart
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
    
    /**
     * private method to move past any white space characters starting at
     * str.charAt(i) and ending when i is not pointing at a whitespace character
     * or i > str.length()
     */
    private void skipWhiteSpace(String str) {
	while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
	        i++;
	} // while
    } // skipWhiteSpace(String)
} // class JSONObject
