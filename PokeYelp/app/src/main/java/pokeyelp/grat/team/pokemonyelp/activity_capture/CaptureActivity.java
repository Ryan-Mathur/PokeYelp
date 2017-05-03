package pokeyelp.grat.team.pokemonyelp.activity_capture;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.Collection;
import pokeyelp.grat.team.pokemonyelp.activity_collection.CollectionActivity;
import pokeyelp.grat.team.pokemonyelp.activity_collection.PokemonBusinessSQLiteOpenHelper;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchActivity;
import pokeyelp.grat.team.pokemonyelp.constants.Pokemon;
import pokeyelp.grat.team.pokemonyelp.helpers.ShareOnSocialMedia;

public class CaptureActivity extends AppCompatActivity {
    public static final String TAG = " CHECKING FOR ERRORS: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        //todo just one pokemon is on the screen 
        ImageView pokemonSprite = (ImageView) findViewById(R.id.capture_pokemon);
        String imageUrl = Pokemon.POKEMON_SPRITE_BASE_URL +  + ".gif";
        Ion.with(CaptureActivity.this).load(imageUrl).intoImageView(pokemonSprite);

        //at the bottom of the screen 
        final PokemonBusinessSQLiteOpenHelper mHelper = PokemonBusinessSQLiteOpenHelper.getInstance(this);
        Button captureButton = (Button) findViewById(R.id.capture_button);
        //todo button saves the pokemon id to the db
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(CaptureActivity.this, CollectionActivity.class);
//                intent.putExtra("pokemon_name", "pikachu");
//                intent.putExtra("pokemon_id", 1);
//                intent.putExtra("yelp_id", "burger_king");
//                startActivity(intent);
                mHelper.addToCollection("pikachu", 1, "burker_king");
                Log.d(TAG, "CAPTURE ACTIVITY onClick: " );
            }
        });

        //return to screen that they launched from 
        //dialog that say you captured this pokemon 
        //with a button for sharing 
        AlertDialog.Builder builder = new AlertDialog.Builder(CaptureActivity.this);
        builder.setTitle("Captured!")
                .setMessage("Good Job! You caught a pokemon. Keep up the good work.")
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShareOnSocialMedia.share(CaptureActivity.this, "I caught a Pokemon playing PokemonYelp. Play with me!");
                    }
                })
                .setNegativeButton("I'm done.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                        .show();






    }
}
