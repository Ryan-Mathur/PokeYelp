package pokeyelp.grat.team.pokemonyelp.activity_capture;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.PokemonBusinessSQLiteOpenHelper;
import pokeyelp.grat.team.pokemonyelp.constants.IntentCode;
import pokeyelp.grat.team.pokemonyelp.constants.Pokemon;
import pokeyelp.grat.team.pokemonyelp.gson_pokemon_species.Species;
import pokeyelp.grat.team.pokemonyelp.helpers.ShareOnSocialMedia;

public class CaptureActivity extends AppCompatActivity {
    public static final String TAG = " CHECKING FOR ERRORS: ";
    private String mPokemonName;
    private Species mCurrentPokemon;
    private String mYelpId;
    private int mPokemonNumber;
    private PokemonBusinessSQLiteOpenHelper mHelper;
    private boolean mCaught = false;
    private String mDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        mPokemonName = getIntent().getStringExtra(IntentCode.POKEMON_NAME_TO_CATCH);
        mDisplayName = mPokemonName.substring(0, 1).toUpperCase() + mPokemonName.substring(1);
        mYelpId = getIntent().getStringExtra(IntentCode.YELP_ID_TO_CATCH);
        mPokemonNumber = getIntent().getIntExtra(IntentCode.POKEMON_NUMBER_TO_CATCH, 0);
        mHelper = PokemonBusinessSQLiteOpenHelper.getInstance(this);


        final ImageView pokemonSprite = (ImageView) findViewById(R.id.capture_pokemon);
        String imageUrl = Pokemon.POKEMON_SPRITE_BASE_URL + mPokemonName + ".gif";
        Ion.with(CaptureActivity.this).load(imageUrl).intoImageView(pokemonSprite);

        //Pokemon name
        ((TextView) findViewById(R.id.pokemon_name)).setText(mDisplayName);

        //at the bottom of the screen 
        final TextView captureButton = (TextView) findViewById(R.id.capture_button);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCaught){
                    shareThis();

                } else {
                    ((TextView) findViewById(R.id.pokemon_name)).setText(mDisplayName + " caught!");
                    pokemonSprite.setImageResource(R.drawable.pokeball);
                    mHelper.addToCollection(mPokemonName, mPokemonNumber, mYelpId);
                    captureButton.setText("Share!");

                    boolean noPokemon = mHelper.storeTableIsEmpty();
                    mCaught = true;

                    if (noPokemon) {
                        mHelper.insertPokemonStore(mYelpId, Pokemon.POKEMON_NOT_AVAILABLE);
                    } else {
                        mHelper.updatePokemon(mYelpId, Pokemon.POKEMON_NOT_AVAILABLE);
                    }

                    congratScreen();
                }
            }
        });
    }

    public void congratScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CaptureActivity.this);
        builder.setTitle("Captured!")
                .setMessage("Good Job! You caught a pokemon. Keep up the good work.")
                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        shareThis();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void shareThis(){
        ShareOnSocialMedia.share(CaptureActivity.this, "I caught a "+mPokemonName+ " playing Pokemon Yelp. Play with me!");
    }
}
