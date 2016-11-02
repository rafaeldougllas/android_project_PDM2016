package com.example.rafael.myrecipes.http;


import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import com.example.rafael.myrecipes.model.Recipe;

public class RecipeDeserializer implements JsonDeserializer<Recipe> {

    @Override
    public Recipe deserialize(JsonElement json,
                             Type typeOfT,
                             JsonDeserializationContext context) throws JsonParseException {
        try {
            JsonObject jsonObject = (JsonObject) json;
            Recipe recipe = new Recipe();
            recipe.setTitle(jsonObject.get("title").getAsString());
            recipe.setF2f_url(jsonObject.get("f2f_url").getAsString());
            recipe.setImage_url(jsonObject.get("image_url").getAsString());
            recipe.setPublisher(jsonObject.get("publisher").getAsString());
            recipe.setRecipe_id(jsonObject.get("recipe_id").getAsString());
            recipe.setPublisher_url(jsonObject.get("publisher_url").getAsString());
            recipe.setSocial_rank(jsonObject.get("social_rank").getAsDouble());
            recipe.setSource_url(jsonObject.get("source_url").getAsString());
            if(jsonObject.get("ingredients").getAsJsonArray().size() > 0){
                JsonArray ingredientsJsonArr = jsonObject.get("ingredients").getAsJsonArray();
                String[] arrayIngredients = new String[ingredientsJsonArr.size()];
                for(int i=0;i<ingredientsJsonArr.size();i++){
                    Log.d("RDSBS",""+ingredientsJsonArr.get(i));
                    arrayIngredients[i] = String.valueOf(ingredientsJsonArr.get(i));
                }
                recipe.setIngredients(arrayIngredients);
            }


            return recipe;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    float asFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}