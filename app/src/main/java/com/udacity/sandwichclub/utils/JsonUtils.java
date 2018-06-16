package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            //Convert String into JSON Object
            JSONObject sandwichJSON = new JSONObject(json);
            //Access object inside JSON Object
            JSONObject name = sandwichJSON.getJSONObject("name");
            //Access String from JSON Object
            String mainName = name.getString("mainName");

            //Access JSON array
            JSONArray akaJSON = name.getJSONArray("alsoKnownAs");
            List<String> aka = new ArrayList<>();
            if (akaJSON.length() > 0) {
                for (int i = 0; i < akaJSON.length(); i++) {
                    aka.add(akaJSON.getString(i));
                }
            }

            String placeOfOrigin = sandwichJSON.getString("placeOfOrigin");
            String description = sandwichJSON.getString("description");
            String image = sandwichJSON.getString("image");

            JSONArray ingredientsJSON = sandwichJSON.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            if (ingredientsJSON.length() > 0) {
                for (int i = 0; i < ingredientsJSON.length(); i++) {
                    ingredients.add(ingredientsJSON.getString(i));
                }
            }
            //Create Sandwich object based on JSON data and return via 'sandwich' variable
            sandwich = new Sandwich(mainName, aka, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            System.out.println("JSON parsing error occurred.");
            e.printStackTrace();
        }
        return sandwich;
    }
}
