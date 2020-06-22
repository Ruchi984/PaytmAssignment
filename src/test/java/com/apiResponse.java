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
	
	public boolean movieCodeUnique(String str)
	{
		for (int i=0;i<str.length();i++)
		{
			char ch=str.charAt(i);
			int count=0;

			for (int j=0;j<str.length();j++)
			{
				if (ch==str.charAt(j))
				{
					count++;
				}
				if (count>1)
				{
					return false;
				}
			}
		}

		return true;

	}

	

}
