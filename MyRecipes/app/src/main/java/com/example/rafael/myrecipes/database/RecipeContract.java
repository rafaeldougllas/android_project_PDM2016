package com.example.rafael.myrecipes.database;

import android.provider.BaseColumns;

public interface RecipeContract extends BaseColumns {
    // Nome da tabela no banco de dados
    String TABLE_NAME = "Recipes";

    // Colunas do banco de dados
    String COL_RECIPES_ID    = "recipe_id";
    String COL_PUBLISHER     = "publisher";
    String COL_F2F_URL       = "f2f_url";
    String COL_SOURCE_URL    = "source_url";
    String COL_IMAGE_URL     = "image_url";
    String COL_SOCIAL_RANK   = "social_rank";
    String COL_PUBLISHER_URL = "publisher_url";
    String COL_TITLE         = "title";
    String COL_INGREDIENTS   = "ingredients";


    // Colunas utilizadas pelo adapter do fragment de favoritos
    String[] LIST_COLUMNS = new String[]{
            RecipeContract._ID,
            RecipeContract.COL_RECIPES_ID,
            RecipeContract.COL_PUBLISHER,
            RecipeContract.COL_TITLE,
            RecipeContract.COL_IMAGE_URL
    };
}
