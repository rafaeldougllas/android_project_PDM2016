package com.example.rafael.myrecipes.http;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.rafael.myrecipes.model.Recipe;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeHttp {

    public static final String URL_SEARCH_BY_TITLE         = "http://food2fork.com/api/search?key=877e7eec71e1608531c3ea7a35720e43&q=%s";
    public static final String URL_SEARCH_BY_F2F_RECIPE_ID = "http://food2fork.com/api/get?key=877e7eec71e1608531c3ea7a35720e43&rId=%s";

    public static List<Recipe> searchRecipes(String query){
        List<Recipe> recipes = new ArrayList<>();

        // Abre a conexão com o servidor
        OkHttpClient client = new OkHttpClient();
        String url = String.format(URL_SEARCH_BY_TITLE, query);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;

        // Realiza a chamada ao servidor
        try {
            response = client.newCall(request).execute();
            // response.body retorna o corpo da resposta, que no nosso caso é JSON
            String json = response.body().string();

            // Esse JSON retorna um objeto JSON onde a propriedade "Search" traz
            // a lista dos resultados. Por isso, obtemos o JSONArray com esse resultado
            // e só então passamos para o GSON ler.
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("recipes");
            String jsonList = jsonArray.toString();

            Gson gson = new Gson();
            Recipe[] recipesArray = gson.fromJson(jsonList, Recipe[].class);
            recipes.addAll(Arrays.asList(recipesArray));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return recipes;
    }

    public static Recipe loadRecipeById(String recipeId){

        // Abre a conexão com o servidor
        OkHttpClient client = new OkHttpClient();
        String url = String.format(URL_SEARCH_BY_F2F_RECIPE_ID, recipeId);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            // Realiza a chamada ao servidor
            response = client.newCall(request).execute();

            // response.body retorna o corpo da resposta, que no nosso caso é JSON
            String json = response.body().string();

            JSONObject jsonObject  = new JSONObject(json);
            JSONObject jsonObject2 = jsonObject.getJSONObject("recipe");
            String jsonStr = jsonObject2.toString();
            // Essa resposta já traz apenas um objeto com todas as informações da receita
            // Então é só passar para o JSON fazer o parser
            GsonBuilder gsonBuilder = new GsonBuilder();
            RecipeDeserializer deserializer = new RecipeDeserializer();
            gsonBuilder.registerTypeAdapter(Recipe.class, deserializer);
            Gson gson = gsonBuilder.create();
            Recipe re = gson.fromJson(jsonStr, Recipe.class);
            return re;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
