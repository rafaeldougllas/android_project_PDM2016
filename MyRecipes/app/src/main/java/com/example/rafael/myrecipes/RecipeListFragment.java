package com.example.rafael.myrecipes;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.example.rafael.myrecipes.http.RecipesSearchTask;
import com.example.rafael.myrecipes.model.Recipe;


public class RecipeListFragment extends Fragment
        implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<List<Recipe>>
{
    private static final String QUERY_PARAM = "param";
    public static final int LOADER_ID = 0;

    SearchView mSearchView;
    RecyclerView mRecyclerView;
    RecipeAdapter mAdapter;
    List<Recipe> mRecipesList;
    LoaderManager mLoaderManager;
    View mEmptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mRecipesList = new ArrayList<>();
        mAdapter = new RecipeAdapter(getActivity(), mRecipesList);
        mAdapter.setRecipeClickListener(new OnRecipeClickListener() {
            @Override
            public void onRecipeClick(View view, Recipe recipe, int position) {
                // Nessa abordagem o click é mais lento,
                // mas não precisamos usar um atributo adicional
                Activity activity = getActivity();
                if (activity instanceof OnRecipeClickListener){
                    ((OnRecipeClickListener)activity).onRecipeClick(view, recipe, position);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        mEmptyView = view.findViewById(R.id.empty_view_root);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.main_recycler_recipes);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
                && getResources().getBoolean(R.bool.phone)) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setAdapter(mAdapter);

        mLoaderManager = getLoaderManager();
        mLoaderManager.initLoader(LOADER_ID, null, this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(this);
    }

    // ---- OnQueryTextListener
    @Override
    public boolean onQueryTextSubmit(String query) {
        Bundle params = new Bundle();
        params.putString(QUERY_PARAM, query);
        mLoaderManager.restartLoader(LOADER_ID, params, this);
        mSearchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    // ---- LoaderManager.LoaderCallbacks
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        String s = args != null ? args.getString(QUERY_PARAM) : null;
        return new RecipesSearchTask(getContext(), s, null);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        mRecipesList.clear();
        if (data != null && data.size() > 0){
            mRecipesList.addAll(data);
            mEmptyView.setVisibility(View.GONE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }
}
