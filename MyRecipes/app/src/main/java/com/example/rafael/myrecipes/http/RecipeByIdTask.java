package com.example.rafael.myrecipes.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.rafael.myrecipes.model.Recipe;


// Essa tarefa está carregando as informações da receita baseada no ID do F2f
public class RecipeByIdTask extends AsyncTaskLoader<Recipe> {

    private Recipe mRecipe;
    private String mId;

    public RecipeByIdTask(Context context, String id) {
        super(context);
        mId = id;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mRecipe == null || !mRecipe.equals(mId)) {
            forceLoad();
        }
        else {
            deliverResult(mRecipe);
        }
    }

    @Override
    public Recipe loadInBackground() {
        mRecipe = RecipeHttp.loadRecipeById(mId);
        return mRecipe;
    }
}
