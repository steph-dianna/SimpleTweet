package com.codepath.apps.restclienttemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entities {

public Entities() {}

    public String media_url;
    public String type;

    public static com.codepath.apps.restclienttemplate.Entities fromJson(JSONObject jsonObject) throws JSONException {

        com.codepath.apps.restclienttemplate.Entities entities = new com.codepath.apps.restclienttemplate.Entities();
        if (jsonObject.has("media")){
            final JSONArray media_ar = jsonObject.getJSONArray("media");
            entities.media_url = media_ar.getJSONObject(0).getString("media_url_https");
        }
        else {
            entities.media_url="";
        }
        entities.type = jsonObject.has("type") ? jsonObject.getString("type"):"";
        return entities;
    }


}
