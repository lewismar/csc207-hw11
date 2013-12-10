package edu.grinnell.csc207.lewismar.json;

public class JSONObject {

    String key;
    Object value;
    static int i = 0; // global variable to traverse the string

    public JSONObject(String str) throws Exception {

	// Find the key
	i = 1; // to traverse str, start at 1 because 0 is a quote
	while (str.charAt(i) != '"' && str.charAt(i-1) != '\\') {
	    key += str.charAt(i);
	    i++;
	} // while
	  // were at a colon
	while (str.charAt(i) == ' ') {
	    i++;
	} // while
	  
	// Cases for value:
	  // We have a string
	if (str.charAt(i) == '\"') {
	    this.value = onString(str);
	}
	// We have digits
	else if (Character.isDigit(str.charAt(i))) {
	    String toValue = "";
	    while (i < str.length()) {
		toValue += str.charAt(i);
		i++;
	    } // while
	    this.value = new Double(toValue);
	}
	// We have another object
	else if (str.charAt(i) == '{')
	    value = new JSONObject(inBrace(str));
	// we have an array
	
	// otherwise fail
	else {
	    throw new Exception("invalid input");
	}

    }
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

    }
    /**
     * takes a string that begins with { and returns the substring between that
     * brace and its counterpart
     */
    private static String inBrace(String str) {
	int initial = i + 1;
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
	return str.substring(initial, i - 1);
    } // inBrace(String)

}
