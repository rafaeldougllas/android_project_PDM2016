package com.example.rafael.myrecipes;

import android.view.View;

import com.example.rafael.myrecipes.model.Recipe;

public interface OnRecipeClickListener {
    void onRecipeClick(View view, Recipe recipe, int position);
}