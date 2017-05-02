package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.DBAsset.DBAssetHelper;

public class CollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        DBAssetHelper dbSetup = new DBAssetHelper(CollectionActivity.this);
        dbSetup.getReadableDatabase();

  

    }






}
