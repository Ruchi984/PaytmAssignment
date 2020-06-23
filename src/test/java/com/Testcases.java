package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class Testcases extends apiResponse{

	@Test(priority=0)
	public void teststatuscode() 
	{
	
		Response rep=RestAssured.get("https://apiproxy.paytm.com/v2/movies/upcoming");
		int code=rep.getStatusCode();
		
		Assert.assertEquals(200, code);
		System.out.println("Status Code: "+code);
		
	}
	
	@Test(priority=1)
	public void movieReleaseDateCheck() throws ParseException, java.text.ParseException
	{
		JSONArray jarray=apiArray();
		
			
		for(int i=0;i<jarray.size();i++)
		{
			JSONObject record=(JSONObject)jarray.get(i);
			
			SimpleDateFormat sdformat=new SimpleDateFormat("yyyy-MM-dd");
			String movieReleaseDate =(String)record.get("releaseDate");
			
			String currentDate=sdformat.format(Calendar.getInstance().getTime());
			
			if(movieReleaseDate!=null)
			{
				
			Assert.assertTrue(movieReleaseDate.compareTo(currentDate)>0);			
			System.out.println("Movie releasing dateat index "+i+" is: "+movieReleaseDate);
		
			}
		
		}
		
		
	}
	
	@Test(priority=2)
	public void moviePosterUrljpgFormat() throws ParseException
	{
		JSONArray jarray=apiArray();
		
		for(int i=0;i<jarray.size();i++)
		{
			JSONObject record=(JSONObject)jarray.get(i);
			String moviePosterURL =(String)record.get("moviePosterUrl");
						
			Pattern pat=Pattern.compile("([^\\s]+(\\.(?i)(jpg))$)");
			Matcher matcher=pat.matcher(moviePosterURL);

			Assert.assertTrue(matcher.matches());
			System.out.println("Movie Poster URL at index "+ i +" in .jpg format is: "+moviePosterURL);
		}
		
	}
	
	
	@Test(priority=3)
	public void paytmMovieCodeUnique() throws ParseException
	{
		JSONArray jarray=apiArray();
		List<String> jsonlist = new ArrayList<String>();
		
		for(int k=0;k<jarray.size();k++)
		{
			JSONObject record=(JSONObject)jarray.get(k);
						
			jsonlist.add((String)record.get("paytmMovieCode"));
		}
		
		for(int i=0;i<jsonlist.size();i++)
		{
			for(int j=0;j<jsonlist.size();j++)
			{
				if(i!=j)
				{	
					
				Assert.assertFalse(jsonlist.get(i)==jsonlist.get(j));
				if(jsonlist.get(i)==jsonlist.get(j))
					{
				System.out.println("Movie code is not unique"+jsonlist.get(i)+jsonlist.get(j));
					}
				
				}
			}
			System.out.println("Paytm Movie Code at index " + i+" is Uinque: "+ jsonlist.get(i));
			
		}
	}
	
	@Test(priority=4)
	public void languageFormat() throws ParseException
	{
				
		JSONArray jarray=apiArray();
		for(int i=0;i<jarray.size();i++)
		{
			JSONObject record=(JSONObject)jarray.get(i);
			String languageFormat =(String)record.get("language");
			System.out.println("Language of movie at index "+i+" is: "+languageFormat);
		}
		
	}	
	
	@Test(priority=5)
	public void isContentAvailable() throws ParseException, IOException
	{
		JSONArray jarray=apiArray();
		
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet("Paytm Movie Name");
		
		Row header=sheet.createRow(0);
		header.createCell(0).setCellValue("S.NO.");
		header.createCell(1).setCellValue("Movie Names whose ContentAvailable is 0");
		
		int j=1;
		for(int i=0;i<jarray.size();i++)
		{
			JSONObject record=(JSONObject)jarray.get(i);
			
			String movieName =(String)record.get("movie_name");
			Long ContentAvailable =(Long)record.get("isContentAvailable");
			if(ContentAvailable==0)
			{
				
				Row dataRow=sheet.createRow(j);
				dataRow.createCell(0).setCellValue(j);
				dataRow.createCell(1).setCellValue(movieName);
				j++;							
			}
		}
			
		FileOutputStream fls=new FileOutputStream(new File("Movie_Name.xlsx"));
		workbook.write(fls);
		fls.close();
		System.out.println("Excel sheet successfully created...");	
	}
	
}
