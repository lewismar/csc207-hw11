package edu.grinnell.csc207.lewismar.json;

public class JSONObject {

    String key;
    Object value;

    public JSONObject(String str) {
	String[] strs = str.split("\"");
	this.key = strs[1];

	if (strs[2].contains("[")) {

	}
	if (strs[2].contains("{")) {
	    value = new JSONObject(inBrace(str));
	}
    }

    /**
     * takes a string that begins with { and returns the substring between that
     * brace and its counterpart
     */
    private static String inBrace(String str) {
	int open = 1;
	char c;
	int i = 0;
	while (open > 0) {
	    c = str.charAt(++i);
	    if (c == '{') {
		open++;
	    } else if (c == '}') {
		open--;
	    } // if/else if
	} // while
	return str.substring(1, i);
    } // inBrace(String)

}
