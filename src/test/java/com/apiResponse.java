package com;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class apiResponse {
	
	public JSONArray apiArray() throws ParseException
	{
		
		String URL="https://apiproxy.paytm.com/v2/movies/upcoming";
		Response rep=RestAssured.get(URL);
		
		String data=rep.asString();
		
		JSONParser parser=new JSONParser();
		JSONObject jobj=(JSONObject)parser.parse(data);
		
		JSONArray jarray=(JSONArray)jobj.get("upcomingMovieData");
	
		return jarray;
	}
	
	
	

}
