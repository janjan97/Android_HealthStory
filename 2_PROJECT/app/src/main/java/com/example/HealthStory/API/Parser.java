package com.example.HealthStory.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parser {

    public Parser(){}

    public  String Parser(String data) throws JSONException {
        String meta="";
        try{
            JSONObject jsonObject = new JSONObject(data);
            String item = jsonObject.getString("items");
            JSONArray jsonArray = new JSONArray(item);

            for(int i=0; i<jsonArray.length();i++){
                JSONObject subObject = jsonArray.getJSONObject(i);

                String pr_image = subObject.getString("image");
                String pr_title = subObject.getString("title");
                String pr_price = subObject.getString("lprice");
                String pr_brnad = subObject.getString("brand");

                meta+= (i+1)+ "번째 : 상품" + '\n'+ pr_image + '\n' + pr_title + " 가격 : " + pr_price +'\n' + "브랜드 : " + pr_brnad + '\n';

            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return meta;
    }
}