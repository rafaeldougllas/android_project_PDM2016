package com.example.rafael.myrecipes;

import android.content.Context;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;

import com.example.rafael.myrecipes.database.RecipeContract;
import com.example.rafael.myrecipes.database.RecipesProvider;
import com.example.rafael.myrecipes.model.Recipe;

public class RecipeDetailUtils {

    public static boolean isFavorite(Context ctx, String f2f){
        Cursor cursor = ctx.getContentResolver().query(
                RecipesProvider.RECIPES_URI,
                new String[]{ RecipeContract._ID },
                RecipeContract.COL_RECIPES_ID +" = ?",
                new String[]{ f2f },
                null
        );
        boolean isFavorite = false;
        if (cursor != null) {
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }
        return isFavorite;
    }

    public static void toggleFavorite(Context ctx, FloatingActionButton fab, String f2f){
        if (RecipeDetailUtils.isFavorite(ctx, f2f)){
            fab.setImageResource(R.drawable.ic_favorite);
        } else {
            fab.setImageResource(R.drawable.ic_unfavorite);
        }
    }

    public static Recipe recipeItemFromCursor(Cursor cursor){
        Recipe recipe = new Recipe();
        recipe.setId(cursor.getLong(cursor.getColumnIndex(RecipeContract._ID)));
        recipe.setRecipe_id(cursor.getString(cursor.getColumnIndex(RecipeContract.COL_RECIPES_ID)));
        recipe.setTitle(cursor.getString(cursor.getColumnIndex(RecipeContract.COL_TITLE)));
        recipe.setImage_url(cursor.getString(cursor.getColumnIndex(RecipeContract.COL_IMAGE_URL)));
        recipe.setPublisher(cursor.getString(cursor.getColumnIndex(RecipeContract.COL_PUBLISHER)));
        return recipe;
    }
}
