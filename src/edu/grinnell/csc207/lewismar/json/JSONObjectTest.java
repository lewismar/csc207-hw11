package edu.grinnell.csc207.lewismar.json;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class JSONObjectTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
	JSONObject obj = new JSONObject("{\"name\":\"wheelie\"}");
	assertEquals("wheelie", obj.members.getFirst().value);

	obj = new JSONObject(
		"{\"number\":  10.4E-2,\"bool\":true,\"bool2\":false,\"null\":null}");
	assertEquals(.104, (Double) obj.members.getFirst().value, .01);
	assertEquals(true, obj.members.get(1).value);
	assertEquals(false, obj.members.get(2).value);
	assertEquals(null, obj.members.get(3).value);

	obj = new JSONObject("{\n\"number\": 10.4E-2}");

	obj = new JSONObject(
		"{\n\"Name\"   : \"Sam\", \"Job\":    {\"Employer\":\"Grinnell\",\"Title\":\"Professor of Comp Sci\",\"StartYear\":1997}}");

	assertEquals("Grinnell",
		((JSONObject) obj.members.get(1).value).members.get(0).value);
	assertEquals("Professor of Comp Sci",
		((JSONObject) obj.members.get(1).value).members.get(1).value);
	assertEquals(1997.0,
		((JSONObject) obj.members.get(1).value).members.get(2).value);

	obj = new JSONObject(
		"{\"Arrizle\":[5,1,2,9,3, 23,6,true,false,\"oreos\" ] }");
	Object[] myArrayDotJPG = { 5.0, 1.0, 2.0, 9.0, 3.0, 23.0, 6.0, true,
		false, "oreos" };
	ArrayList<Object> objArr = (ArrayList<Object>) obj.members.get(0).value;
	for (int i = 0; i < 10; i++) {
	    assertEquals("Array test " + i, myArrayDotJPG[i], objArr.get(i));
	} // for

    }

}
