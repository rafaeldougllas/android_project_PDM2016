package com.example.rafael.myrecipes.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.example.rafael.myrecipes.model.Recipe;


// Essa tarefa carrega a lista das receitas baseado nos parâmetros da busca
public class RecipesSearchTask extends AsyncTaskLoader<List<Recipe>> {
    List<Recipe> mRecipes;
    String query;

    public RecipesSearchTask(Context context, String query, List<Recipe> recipes) {
        super(context);
        this.query = query;
        this.mRecipes = new ArrayList<>();
        if (recipes != null) {
            this.mRecipes.addAll(recipes);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (query != null) {
            forceLoad();
        } else {
            deliverResult(mRecipes);
        }
    }

    @Override
    public List<Recipe> loadInBackground() {
        mRecipes.addAll((Collection<? extends Recipe>) RecipeHttp.searchRecipes(query));
        return mRecipes;
    }
}