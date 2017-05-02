package pokeyelp.grat.team.pokemonyelp.activity_detail;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.constants.IntentCode;
import pokeyelp.grat.team.pokemonyelp.constants.Pokemon;
import pokeyelp.grat.team.pokemonyelp.gson_pokemon_species.Species;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.BusinessDetail;
import pokeyelp.grat.team.pokemonyelp.singleton.MrSingleton;

public class DetailActivity extends AppCompatActivity {

    private static final String FAKE_YELP_ID = "coffee-project-new-york-new-york";
    private String mYelpToken;
    private GetYelpBusiness mApiTask;
    private GetPokemon mPokemonTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mYelpToken = MrSingleton.getInstance().getToken();
        String yelpId = getIntent().getStringExtra(IntentCode.BUSINESS_ID_TO_DETAILS);
        mApiTask = new GetYelpBusiness();
        mApiTask.execute(Api.YELP_BASE_URL + "businesses/" + yelpId);
    }

    public void setupViews(BusinessDetail business){
        ((TextView) findViewById(R.id.detail_name)).setText(business.getName());
        ((TextView) findViewById(R.id.detail_adress)).setText(business.getLocation().getAddress1());
        findViewById(R.id.detail_button_scan_pokemon).setVisibility(View.VISIBLE);
        findViewById(R.id.detail_button_scan_pokemon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPokemonTask != null && mPokemonTask.getStatus().equals(AsyncTask.Status.RUNNING)){
                    Toast.makeText(DetailActivity.this, "Already Scanning", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Scanning", Toast.LENGTH_SHORT).show();
                    mPokemonTask = new GetPokemon();
                    mPokemonTask.execute();
                }
            }
        });
    }


    public class GetPokemon extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Species pokemonSpecies = null;

            int tries = 3;

                while(tries>0) {
                    int pokemonRandom = (int) (Pokemon.POKEMON_NUMBERS * Math.random());
                    pokemonRandom++;
                    Request speciesRequest = new Request.Builder()
                            .url(Api.POKEMON_BASE_URL + "pokemon-species/" + pokemonRandom)
                            .build();

                    try {
                        Response speciesResponse = client.newCall(speciesRequest).execute();
                        Gson gson = new Gson();
                        pokemonSpecies = gson.fromJson(speciesResponse.body().string(), Species.class);
                        System.out.println(pokemonSpecies.getName() + " " + pokemonSpecies.getCaptureRate());
                        if (pokemonSpecies.getCaptureRate()>=1) {
                            return pokemonSpecies.getName();
                        }
                        tries--;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                Toast.makeText(DetailActivity.this, "No Pokemon Found!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, s, Toast.LENGTH_SHORT).show();
                ImageView pokemonSprite = (ImageView) findViewById(R.id.detail_pokemon_picture);
                pokemonSprite.setVisibility(View.VISIBLE);
                String imageUrl = Pokemon.POKEMON_SPRITE_BASE_URL + s +".gif";
//                Glide.with(DetailActivity.this).load(imageUrl)
//                        .into(pokemonSprite);
                Ion.with(DetailActivity.this).load(imageUrl).intoImageView(pokemonSprite);

            }
        }
    }

    public class GetYelpBusiness extends AsyncTask<String, Void, BusinessDetail> {

        @Override
        protected BusinessDetail doInBackground(String... urlQuery) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    //yelp requires the header to have the token here for authentication
                    .header("Authorization", "Bearer " + mYelpToken)

                    //the query here is the query we passed to it above
                    .url(urlQuery[0])
                    .build();

            try {
                Response response = client.newCall(request).execute();
                //here we use gson to convert json data to java object
                Gson gson = new Gson();
                //because of the way I set up the java classes, we only need this ONE line to get ALL
                //the data from the JSON response. Check out the classes in the yelp folder to see
                //all the stuff we've got to work with. It's a lot!!
                BusinessDetail business = gson.fromJson(response.body().string(), BusinessDetail.class);
                //Because this is in the try block, we are going to try to access something from the business
                //If we get a bad business, it wil cause a null pointer exception, which we can catch.
                //We do this because yelp will give back something even if we give it random crap, and if
                //we just take whatever they give us, it will cause a problem late.
                business.getLocation().getAddress1();
                //starts the onPostExecute method, and gives it the ApiResult object
                return business;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(BusinessDetail business) {
            super.onPostExecute(business);
            if (business!=null){
                setupViews(business);
            } else {
                Toast.makeText(DetailActivity.this, "Business not found", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
