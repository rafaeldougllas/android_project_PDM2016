package com.example.rafael.myrecipes;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rafael.myrecipes.database.RecipeContract;

public class RecipeCursorAdapter extends SimpleCursorAdapter {

    private static final int LAYOUT = R.layout.item_recipe;

    public RecipeCursorAdapter(Context context, Cursor c) {
        super(context, LAYOUT, c, RecipeContract.LIST_COLUMNS, null, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(LAYOUT, parent, false);

        VH vh = new VH();
        vh.imageViewPhoto    = (ImageView) view.findViewById(R.id.recipe_item_image);
        vh.textViewTitle     = (TextView) view.findViewById(R.id.recipe_item_text_title);
        vh.textViewPublisher = (TextView) view.findViewById(R.id.recipe_item_text_publisher);
        view.setTag(vh);

        ViewCompat.setTransitionName(vh.imageViewPhoto, "imageRecipe");
        ViewCompat.setTransitionName(vh.textViewTitle, "title");
        ViewCompat.setTransitionName(vh.textViewPublisher, "publisher");

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String poster = cursor.getString(cursor.getColumnIndex(RecipeContract.COL_IMAGE_URL));
        String title = cursor.getString(cursor.getColumnIndex(RecipeContract.COL_TITLE));
        String publisher = cursor.getString(cursor.getColumnIndex(RecipeContract.COL_PUBLISHER));

        VH vh = (VH)view.getTag();
        Glide.with(context)
                .load(poster)
                .placeholder(R.mipmap.ic_recipe)
                .into(vh.imageViewPhoto);
        vh.textViewTitle.setText(title);
        vh.textViewPublisher.setText(publisher);
    }

    class VH {
        ImageView imageViewPhoto;
        TextView textViewTitle;
        TextView textViewPublisher;
    }
}
