package pokeyelp.grat.team.pokemonyelp.activity_detail;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

public class DetailActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String mYelpToken;
    private GetYelpBusiness mApiTask;
    private GetPokemon mPokemonTask;
    private String mCurrentPokemon;
    private Location mLocation;
    private BusinessDetail mBusinessDetail;
    private static final int REQUEST_LOCATION = 15;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mYelpToken = MrSingleton.getInstance().getToken();
        String yelpId = getIntent().getStringExtra(IntentCode.BUSINESS_ID_TO_DETAILS);
        mApiTask = new GetYelpBusiness();
        mApiTask.execute(Api.YELP_BASE_URL + "businesses/" + yelpId);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void setupViews(BusinessDetail business){
        mBusinessDetail = business;
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

    public void showPokemon(){
        if (mCurrentPokemon == null) {
            Toast.makeText(DetailActivity.this, "No Pokemon Found!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailActivity.this, mCurrentPokemon, Toast.LENGTH_SHORT).show();
            ImageView pokemonSprite = (ImageView) findViewById(R.id.detail_pokemon_picture);
            pokemonSprite.setVisibility(View.VISIBLE);
            TextView catchButton = (TextView) findViewById(R.id.detail_button_catch_pokemon);
            catchButton.setVisibility(View.VISIBLE);
            String imageUrl = Pokemon.POKEMON_SPRITE_BASE_URL + mCurrentPokemon +".gif";
            Ion.with(DetailActivity.this).load(imageUrl).intoImageView(pokemonSprite);
            catchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getLocation();
                }
            });
        }
    }


    public void getLocation(){
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLocation!=null) {
                catchPokemon();
            }else{
                Toast.makeText(this, "Cannot determine location! Please try again", Toast.LENGTH_SHORT).show();
            }


        } else {
            ActivityCompat.requestPermissions(DetailActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }

    public void catchPokemon(){
        Location pokemonLocation = new Location("");
        pokemonLocation.setLatitude(mBusinessDetail.getCoordinates().getLatitude());
        pokemonLocation.setLongitude(mBusinessDetail.getCoordinates().getLongitude());
        Toast.makeText(this, "" + mLocation.distanceTo(pokemonLocation), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //getLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getLocation();

                } else {
                    Toast.makeText(this, "Cannot access your location", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;

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
        protected void onPostExecute(String pokemonName) {
            super.onPostExecute(pokemonName);
            mCurrentPokemon = pokemonName;
            showPokemon();
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
