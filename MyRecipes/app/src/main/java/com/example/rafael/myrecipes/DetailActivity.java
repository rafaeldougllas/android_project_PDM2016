package com.example.rafael.myrecipes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.example.rafael.myrecipes.model.Recipe;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE = "recipe"; // vindo dos favoritos

    Recipe mRecipe;
    FloatingActionButton fab;

    LocalBroadcastManager mLocalBroadcastManager;
    RecipeReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // A MainActivity passará um objeto Recipe,
        // então criamos o fragment de detalhe com esse objeto
        mRecipe = (Recipe) getIntent().getSerializableExtra(EXTRA_RECIPE);
        DetailRecipeFragment detailRecipeFragment;
        detailRecipeFragment = DetailRecipeFragment.newInstance(mRecipe);

        // Todas as informações da receita estão no DetailRecipeFragment,
        // exceto a capa que já carregamos aqui, uma vez que essa informação
        // já existe no objeto Recipe.
        ImageView imgRecipe = (ImageView)findViewById(R.id.detail_image_poster);
        ViewCompat.setTransitionName(imgRecipe, "imageRecipe");
        Glide.with(imgRecipe.getContext()).load(mRecipe.getImage_url()).into(imgRecipe);

        // Esse receiver detectará se a Recipe(Receita) foi adicionada ou removida das favoritas
        // TODO Substituir pelo EventBus?
        mReceiver = new RecipeReceiver();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(RecipeEvent.RECIPE_LOADED));
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(RecipeEvent.RECIPE_FAVORITE_UPDATED));

        // O FAB faz parte do layout da Activity, mas precisa ser atualizado
        // quando o recipe é inserido ou removido dos favoritos. mReceiver fará isso ;)
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Quando clicamos no botão, estamos avisando ao fragment de detalhes para
                // inserir/remover o Recipe no banco. Veja DetailRecipeFragment.RecipeEventReceiver.
                Intent it = new Intent(RecipeEvent.UPDATE_FAVORITE);
                mLocalBroadcastManager.sendBroadcast(it);
            }
        });

        if (savedInstanceState == null) {
            // Adicionando o fragment de detalhes na tela
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholderDetail, detailRecipeFragment)
                    .commit();
        }

        //TODO barra de status transparente?
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    // Esse receiver atualizará o status do botão de favoritos.
    class RecipeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fab.setVisibility(View.VISIBLE);
            Recipe recipe = (Recipe)intent.getSerializableExtra(RecipeEvent.EXTRA_RECIPE);
            RecipeDetailUtils.toggleFavorite(context, fab, recipe.getRecipe_id());
        }
    }
}
