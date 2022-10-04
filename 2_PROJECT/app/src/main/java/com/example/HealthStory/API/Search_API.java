package com.example.HealthStory.API;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Search_API extends AsyncTask<String, Void, String> {

    String clientSecret = "8AcshnXHYb";


    private String receiveMsg;
    private int display = 50;
    private final String clientId = "G0OreXrqLebkjAbGujte";
    String text;

    public void setText(String text){
        this.text=text;
    }
    @Override
    protected String doInBackground(String... strings) {
        try {
            text = URLEncoder.encode("닭 가슴살", "UTF-8");

            String apiURL = "https://openapi.naver.com/v1/search/shop?query="+ text+ "&display=" + display +"&"; // json 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            receiveMsg = response.toString();
            setReceiveMsg(receiveMsg);
        } catch (Exception e) {
            return e.toString();
        }
        return receiveMsg;
    }

    public String getReceiveMsg(){
        return receiveMsg;
    }

    public void setReceiveMsg(String receiveMsg) {
        this.receiveMsg = receiveMsg;
    }
}
