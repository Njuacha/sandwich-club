package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NO_DATA = "Data not available";
    private static JSONObject sandwichJSON;

    public static Sandwich parseSandwichJson(String json) {
        try {

            sandwichJSON = new JSONObject(json);

            return new Sandwich(getMainName(),getOtherNames(),getPlaceOfOrigin(),getDescription(),getImage(),getIngredients());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Extracts the main name from the json object passed
    private static String getMainName() throws JSONException {
        return sandwichJSON.getJSONObject("name").optString("mainName",NO_DATA);
    }

    // Extracts the other names of sandwich from json object passed
    private static List<String> getOtherNames() throws JSONException{

        JSONArray otherNamesJSON = sandwichJSON.getJSONObject("name").getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<>();
        for(int i= 0; i < otherNamesJSON.length(); i++){
            alsoKnownAs.add(otherNamesJSON.getString(i));
        }
        return alsoKnownAs;
    }

    private static String getPlaceOfOrigin() throws JSONException{
        return sandwichJSON.optString("placeOfOrigin",NO_DATA);
    }

    private static String getDescription() throws JSONException{
        return sandwichJSON.optString("description",NO_DATA);
    }

    private static String getImage() throws JSONException{
        return sandwichJSON.optString("image", NO_DATA);
    }

    private static List<String> getIngredients() throws JSONException{
        JSONArray ingredientsJSON = sandwichJSON.getJSONArray("ingredients");
        List<String> ingredients = new ArrayList<>();
        for(int i= 0; i < ingredientsJSON.length(); i++){
            ingredients.add(ingredientsJSON.getString(i));
        }
        return ingredients;
    }











}
