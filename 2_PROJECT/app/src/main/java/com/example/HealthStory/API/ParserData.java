package com.example.HealthStory.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ParserData {

    public ArrayList<String> TitleParserData(String data) throws JSONException {
        ArrayList<String> titlef = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        String item = jsonObject.getString("items");
        JSONArray jsonArray = new JSONArray(item);

        for(int i=0; i<jsonArray.length();i++){
            JSONObject subObject = jsonArray.getJSONObject(i);
            String pr_titlef = subObject.getString("title");
            String pr_title = pr_titlef.replaceAll("<b>", " ").replaceAll("</b>"," ");
            titlef.add(pr_title);
        }

        return titlef;
    }

    public ArrayList<String> PriceParser(String data) throws JSONException {
        ArrayList<String> pricef = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        String item = jsonObject.getString("items");

        JSONArray jsonArray = new JSONArray(item);

        for(int i=0; i<jsonArray.length();i++){
            JSONObject subObject = jsonArray.getJSONObject(i);
            String pr_price = subObject.getString("lprice");
            pricef.add(pr_price);
        }

        return pricef;
    }

    public ArrayList<String> BrandParserData(String data) throws JSONException {
        ArrayList<String> brandf = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        String item = jsonObject.getString("items");
        JSONArray jsonArray = new JSONArray(item);

        for(int i=0; i<jsonArray.length();i++){
            JSONObject subObject = jsonArray.getJSONObject(i);
            String pr_title = subObject.getString("brand");
            brandf.add(pr_title);
        }

        return brandf;
    }

    public ArrayList<String> ImageParserData(String data) throws JSONException {
        ArrayList<String> imagef = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        String item = jsonObject.getString("items");
        JSONArray jsonArray = new JSONArray(item);

        for(int i=0; i<jsonArray.length();i++){
            JSONObject subObject = jsonArray.getJSONObject(i);
            String pr_title = subObject.getString("image");
            imagef.add(pr_title);
        }

        return imagef;
    }

    public ArrayList<String> ProductIdParserData(String data) throws JSONException {
        ArrayList<String> pdtIdf = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(data);
        String item = jsonObject.getString("items");
        JSONArray jsonArray = new JSONArray(item);

        for(int i=0; i<jsonArray.length();i++){
            JSONObject subObject = jsonArray.getJSONObject(i);
            String pr_title = subObject.getString("productId");
            pdtIdf.add(pr_title);
        }

        return pdtIdf;
    }
    public ParserData(){}
}
