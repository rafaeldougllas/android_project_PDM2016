package com.example.rafael.myrecipes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipeDBHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "dbRecipes";
    private static final int DB_VERSION = 1;

    public RecipeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE "+ RecipeContract.TABLE_NAME +" (" +
                        RecipeContract._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RecipeContract.COL_RECIPES_ID  +" TEXT UNIQUE NOT NULL, "+
                        RecipeContract.COL_TITLE    +" TEXT NOT NULL, "+
                        RecipeContract.COL_INGREDIENTS   +" TEXT, "+
                        RecipeContract.COL_PUBLISHER    +" TEXT, "+
                        RecipeContract.COL_F2F_URL +" TEXT, "+
                        RecipeContract.COL_SOURCE_URL     +" TEXT, "+
                        RecipeContract.COL_IMAGE_URL   +" TEXT, "+
                        RecipeContract.COL_SOCIAL_RANK  +" TEXT, "+
                        RecipeContract.COL_PUBLISHER_URL   +" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
