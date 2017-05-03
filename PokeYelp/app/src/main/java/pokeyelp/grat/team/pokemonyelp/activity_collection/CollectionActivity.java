package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.helpers.ToolBar;

public class CollectionActivity extends AppCompatActivity {
    private RecyclerView.Adapter mAdapter;
    private PokemonBusinessSQLiteOpenHelper mHelper;

    List<Collection> collections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        ToolBar.setupSimpleToolbar(findViewById(android.R.id.content));

        mHelper = PokemonBusinessSQLiteOpenHelper.getInstance(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.collection_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        collections = mHelper.getYourCollection();
        mAdapter = new CollectionRecyclerViewAdapater(collections);
        recyclerView.setAdapter(mAdapter);

        mHelper.getYourCollection();


        /*------- TEST --------- CODE ------------------------
        mHelper.addToCollection("pikachu", 1, "burker_king");
        mHelper.addToCollection("charmander", 2,  "starbucks");
        mHelper.addToCollection("electrode", 3, "maison_kayser");
        mHelper.addToCollection("lapras", 4, "trader_joes");

        List<Collection> collectionsList = mHelper.getYourCollection();

        System.out.println(collectionsList.get(0).getPokemonName());
        System.out.println(collectionsList.get(1).getPokemonName());

        mHelper = PokemonBusinessSQLiteOpenHelper.getInstance(this);
        mHelper.resetStores();
        mHelper.insertPokemonStore("some_id", "mewtwo");
        System.out.println(mHelper.searchForPokemon("some_id"));

        mHelper.updatePokemon("some_id", null);
        System.out.println(mHelper.searchForPokemon("some_id"));
        mHelper.addToCollection("charmander", "randomStore");
        mHelper.addToCollection("pikachu", "GAnyu");

        List<Collection> collectionsList = mHelper.getYourCollection();
        System.out.println(collectionsList.get(0).getPokemonName());
        System.out.println(collectionsList.get(1).getPokemonName());*/


    }






}
