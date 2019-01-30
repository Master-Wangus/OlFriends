package com.example.wesle.olfriends;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/* News Feature */

public class JsonParser {
    private JSONObject jsonData;
    public JsonParser(String jsonData)throws JSONException {
        this.jsonData=new JSONObject(jsonData);
    }
    protected HashMap<String,String> getSourceName(){
        HashMap<String,String> sourcesName=new HashMap<>();
        try {
            JSONArray sources = jsonData.getJSONArray("sources");
            for(int i=0;i<sources.length();i++){
                JSONObject object=new JSONObject(sources.get(i).toString());
                sourcesName.put(object.getString("id"),object.getString("name"));
            }
        }
        catch (JSONException e){
            Message.logMessage("ERROR: ",e.toString());
        }
        return sourcesName;
    }
    protected JSONArray getArticles(){
        JSONArray array=new JSONArray();
        try {
            array = jsonData.getJSONArray(Constant.ARTICLES);
        }
        catch (JSONException e){
            Message.logMessage("ERROR: ",e.toString());
        }
        return array;
    }
}
