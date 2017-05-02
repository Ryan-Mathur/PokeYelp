package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import pokeyelp.grat.team.pokemonyelp.R;

public class CollectionActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PokemonBusinessSQLiteOpenHelper mHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

//        mHelper = PokemonBusinessSQLiteOpenHelper.getInstance(this);
//
//        mHelper.resetStores();
//
//        mHelper.insertPokemonStore("some_id", "mewtwo");
//
//        System.out.println(mHelper.searchForPokemon("some_id"));
//
//        mHelper.updatePokemon("some_id", null);
//
//        System.out.println(mHelper.searchForPokemon("some_id"));
//
//        mHelper.addToCollection("charmander", "randomStore");
//        mHelper.addToCollection("pikachu", "GAnyu");
//
//        List<Collection> collectionsList = mHelper.getYourCollection();
//
//        System.out.println(collectionsList.get(0).getPokemonName());
//        System.out.println(collectionsList.get(1).getPokemonName());


    }






}
