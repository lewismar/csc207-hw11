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
	    value = new JSONObject(str.inBrace);
	}
    }
}
