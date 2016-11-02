package com.example.rafael.myrecipes.database;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class RecipesProvider extends ContentProvider {

    private static final String PATH = "recipes";
    private static final String AUTHORITY = "com.example.rafael.myrecipes";

    // BASE_URI   = "content://" + AUTHORITY +"/"+ PATH
    public static Uri BASE_URI = Uri.parse("content://"+ AUTHORITY);

    // MOVIES_URI = "content://" + AUTHORITY +"/"+ PATH +"/recipes"
    public static Uri RECIPES_URI = BASE_URI.withAppendedPath(BASE_URI, PATH);


    private static final int TYPE_GENERIC = 0;
    private static final int TYPE_ID = 1;

    private UriMatcher mMatcher;
    private RecipeDBHelper mHelper;

    public RecipesProvider() {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mMatcher.addURI(AUTHORITY, PATH, TYPE_GENERIC);
        mMatcher.addURI(AUTHORITY, PATH +"/#", TYPE_ID);
    }

    @Override
    public boolean onCreate() {
        mHelper = new RecipeDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        int uriType = mMatcher.match(uri);
        switch (uriType){
            case TYPE_GENERIC:
                return ContentResolver.CURSOR_DIR_BASE_TYPE +"/"+ AUTHORITY;
            case TYPE_ID:
                return ContentResolver.CURSOR_ITEM_BASE_TYPE +"/"+ AUTHORITY;
            default:
                throw new IllegalArgumentException("Invalid Uri");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = mMatcher.match(uri);
        // Como não temos o ID no momento da inserção, só aceitamos
        // inserir usando a Uri genérica.
        if (uriType == TYPE_GENERIC){
            SQLiteDatabase db = mHelper.getWritableDatabase();
            long id = db.insert(RecipeContract.TABLE_NAME, null, values);
            db.close();
            // Se der erro na inclusão o id retornado é -1,
            // então levantamos a exceção para ser tratada na tela.
            if (id == -1){
                throw new RuntimeException("Error inserting moving.");
            }
            notifyChanges(uri);
            return ContentUris.withAppendedId(RECIPES_URI, id);

        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = mMatcher.match(uri);
        // Nossa implementação só aceita a exclusão baseada no id da receita no banco
        if (uriType == TYPE_ID){
            SQLiteDatabase db = mHelper.getWritableDatabase();
            long id = ContentUris.parseId(uri);
            int rowsAffected = db.delete(
                    RecipeContract.TABLE_NAME,
                    RecipeContract._ID +" = ?",
                    new String[] { String.valueOf(id) } );
            db.close();
            if (rowsAffected == 0){
                throw new RuntimeException("Fail deleting recipe");
            }
            notifyChanges(uri);


            return rowsAffected;

        } else {
            throw new IllegalArgumentException("Invalid Uri");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new IllegalArgumentException("Invalid Uri");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int uriType = mMatcher.match(uri);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor;

        switch (uriType){
            // Esse tipo faz uma busca genérica. Estamos usando ele na listagem dos favoritos
            // e para checar se um receita é favorito (ou seja, se já existe no banco)
            case TYPE_GENERIC:
                cursor = db.query(RecipeContract.TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;

            // Esse segundo tipo de Uri está sendo usado na tela de detalhes
            // para trazer todas as informações da receita.
            case TYPE_ID:
                long id = ContentUris.parseId(uri);
                cursor = db.query(RecipeContract.TABLE_NAME,
                        projection, RecipeContract._ID +" = ?",
                        new String[] { String.valueOf(id) }, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Invalid Uri");
        }
        // Essa linha está definindo a Uri que será notificada para que o cursor
        // seja atualizado. Veja método notifyChanges abaixo.
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    private void notifyChanges(Uri uri) {
        // Caso a operação no banco ocorra sem problemas, notificamos a Uri
        // para que a listagem de favoritos seja atualizada.
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
