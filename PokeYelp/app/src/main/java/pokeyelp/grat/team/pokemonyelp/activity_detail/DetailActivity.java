package pokeyelp.grat.team.pokemonyelp.activity_detail;

import android.Manifest;
import android.content.Intent;
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
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_capture.CaptureActivity;
import pokeyelp.grat.team.pokemonyelp.activity_collection.PokemonBusinessSQLiteOpenHelper;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.constants.GameSettings;
import pokeyelp.grat.team.pokemonyelp.constants.IntentCode;
import pokeyelp.grat.team.pokemonyelp.constants.Pokemon;
import pokeyelp.grat.team.pokemonyelp.gson_pokemon_species.Species;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.BusinessDetail;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Hour;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Open;
import pokeyelp.grat.team.pokemonyelp.helpers.ToolBar;
import pokeyelp.grat.team.pokemonyelp.helpers.ViewHelper;
import pokeyelp.grat.team.pokemonyelp.singleton.MrSingleton;

public class DetailActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String mYelpId;
    private String mYelpToken;
    private BusinessDetail mBusinessDetail;
    private GetYelpBusiness mApiTask;
    private GetPokemon mPokemonTask;
    private String mDBPokemon;
    private Species mCurrentPokemon;
    private Location mLocation;
    private static final int REQUEST_LOCATION = 15;
    private GoogleApiClient mGoogleApiClient;
    private PokemonBusinessSQLiteOpenHelper mDbHelper;
    private MrSingleton singleton;
    private TextView mPokemonScan;
    private TextView mPokemonCatch;
    private TextView mPokemonText;
    private TextView mPokedexText;
    private ImageView mPokemonSprite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ToolBar.setupSimpleToolbar(findViewById(android.R.id.content));

        singleton = MrSingleton.getInstance();


        mDbHelper = PokemonBusinessSQLiteOpenHelper.getInstance(this);
        mYelpToken = singleton.getToken();

        mPokemonScan = (TextView) findViewById(R.id.detail_button_scan_pokemon);
        mPokemonCatch = (TextView) findViewById(R.id.detail_button_catch_pokemon);
        mPokemonText = (TextView) findViewById(R.id.detail_pokemon_name);
        mPokedexText = (TextView) findViewById(R.id.detail_pokedex_message);
        mPokemonSprite = (ImageView) findViewById(R.id.detail_pokemon_picture);

        getCurrentBusiness();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    public void getCurrentBusiness(){

        int indexForCurrent = singleton.indexOfYelpId(mYelpId);
        if (indexForCurrent!=-1){
            mBusinessDetail = singleton.getLocalBusinessCache().get(indexForCurrent);
            setupViews(mBusinessDetail);
        } else {
            mYelpId = getIntent().getStringExtra(IntentCode.BUSINESS_ID_TO_DETAILS);
            mApiTask = new GetYelpBusiness();
            mApiTask.execute(Api.YELP_BASE_URL + "businesses/" + mYelpId);
        }
    }

    public void getCurrentPokemon(){
        mDBPokemon = mDbHelper.searchForPokemon(mYelpId);
        if (mDBPokemon==null){
            mPokemonScan.setVisibility(View.VISIBLE);
            mPokemonScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDBPokemon!=null){
                        Toast.makeText(DetailActivity.this, "You already scanned", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mPokemonTask != null && mPokemonTask.getStatus().equals(AsyncTask.Status.RUNNING)){
                        return;
                    } else {
                        mPokemonScan.setVisibility(View.INVISIBLE);
                        mPokedexText.setVisibility(View.VISIBLE);
                        mPokedexText.setText("Scanning...");
                        mPokemonTask = new GetPokemon();
                        mPokemonTask.execute();
                    }
                }
            });
        } else if (mDBPokemon.equals(Pokemon.POKEMON_NOT_AVAILABLE)){
            hidePokemon();
        } else {
            mPokemonScan.setVisibility(View.INVISIBLE);
            mPokedexText.setVisibility(View.VISIBLE);
            mPokedexText.setText("Loading...");
            int indexForCurrent = singleton.indexOfSpeciesName(mDBPokemon);
            if (indexForCurrent!=-1){
                System.out.println("Getting from cache");
                mCurrentPokemon = singleton.getLocalSpeciesCache().get(indexForCurrent);
                showPokemon();
            } else {
                mPokemonTask = new GetPokemon();
                mPokemonTask.execute();
            }
        }
    }



    public void setupViews(BusinessDetail business){
        String categories = "";
        for (int i = 0; i < business.getCategories().size(); i++) {
            categories += business.getCategories().get(i).getTitle() + " ";
        }
        ImageView storePic = (ImageView) findViewById(R.id.detail_image_view);
        if (!business.getImageUrl().trim().isEmpty()||business.getImageUrl() == null) {
            Picasso.with(this).load(business.getImageUrl()).into(storePic);
        } else {
            storePic.setImageResource(R.drawable.box);
        }
        ((TextView) findViewById(R.id.detail_name)).setText(business.getName());
        ((TextView) findViewById(R.id.detail_categories)).setText(categories);
        ImageView starsPic = (ImageView) findViewById(R.id.detail_star_rating);
        ViewHelper.setStars(business.getRating(), starsPic);
        ((TextView) findViewById(R.id.detail_number_reviews)).setText(String.valueOf(business.getReviewCount()));
        String address= ViewHelper.getAddress(business.getLocation());
        if (address.equals(Api.YELP_NO_ADDRESS)){
            address = "";
        } else {
            address += ", ";
        }
        ((TextView) findViewById(R.id.detail_address)).setText(address + business.getLocation().getCity());
        String startHour;
        String endHour = " ";
        try {
            Open open = business.getHours().get(0).getOpen().get(0);
            startHour = open.getStart();
            endHour = open.getEnd();
        } catch (NullPointerException e){
            startHour = null;
        }
        if (startHour == null){
            ((TextView) findViewById(R.id.detail_hours)).setText("Hours not available.");
        } else {
            ((TextView) findViewById(R.id.detail_hours)).setText(startHour + " - " + endHour);
        }


        ((TextView) findViewById(R.id.detail_phone)).setText(business.getPhone());

    }

    public void showPokemon(){
        String pokemonName = mCurrentPokemon.getName();
        String output = pokemonName.substring(0, 1).toUpperCase() + pokemonName.substring(1);
        mPokedexText.setVisibility(View.INVISIBLE);
        mPokemonText.setText(output);
        mPokemonText.setVisibility(View.VISIBLE);
        mPokemonSprite.setVisibility(View.VISIBLE);
        mPokemonCatch.setVisibility(View.VISIBLE);
        String imageUrl = Pokemon.POKEMON_SPRITE_BASE_URL + pokemonName +".gif";
        Ion.with(DetailActivity.this).load(imageUrl).intoImageView(mPokemonSprite);
        mPokemonCatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoogleApiClient.isConnected()){
                    getLocation();
                } else {
                    mGoogleApiClient.connect();
                }
            }
        });
    }

    public void hidePokemon(){
        mPokedexText.setVisibility(View.VISIBLE);
        mPokedexText.setText("No Pokemon Found.");
        mPokemonText.setVisibility(View.INVISIBLE);
        mPokemonSprite.setVisibility(View.INVISIBLE);
        mPokemonCatch.setVisibility(View.INVISIBLE);
        mPokemonScan.setVisibility(View.INVISIBLE);

    }

    public void getLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

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
        double distance = GameSettings.MIN_CAPTURE_DISTANCE + 1;
        try {
            pokemonLocation.setLatitude(mBusinessDetail.getCoordinates().getLatitude());
            pokemonLocation.setLongitude(mBusinessDetail.getCoordinates().getLongitude());
            distance = mLocation.distanceTo(pokemonLocation);
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        if (distance > GameSettings.MIN_CAPTURE_DISTANCE){
            Toast.makeText(this, "You are too far away!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent catchIntent = new Intent(DetailActivity.this, CaptureActivity.class);
            catchIntent.putExtra(IntentCode.POKEMON_NAME_TO_CATCH, mCurrentPokemon.getName());
            catchIntent.putExtra(IntentCode.POKEMON_NUMBER_TO_CATCH, mCurrentPokemon.getId());
            catchIntent.putExtra(IntentCode.YELP_ID_TO_CATCH, mBusinessDetail.getId());
            startActivity(catchIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCurrentPokemon();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLocation();

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


    public class GetPokemon extends AsyncTask<Void, Void, Species>{

        @Override
        protected Species doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Species pokemonSpecies = null;
            String searchPokemon = mDBPokemon;

            if(searchPokemon == null){
                int pokemonRandom = (int) (Pokemon.POKEMON_NUMBERS * Math.random());
                pokemonRandom++;
                searchPokemon = String.valueOf(pokemonRandom);
            }

            Request speciesRequest = new Request.Builder()
                    .url(Api.POKEMON_BASE_URL + "pokemon-species/" + searchPokemon)
                    .build();

            try {
                Response speciesResponse = client.newCall(speciesRequest).execute();
                Gson gson = new Gson();
                pokemonSpecies = gson.fromJson(speciesResponse.body().string(), Species.class);
                System.out.println(pokemonSpecies.getName() + " " + pokemonSpecies.getCaptureRate());
                if (pokemonSpecies.getCaptureRate()>0) {
                    return pokemonSpecies;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Species pokemon) {
            super.onPostExecute(pokemon);
            if (pokemon != null) {
                mCurrentPokemon = pokemon;
                if (mDBPokemon ==null){
                    mDBPokemon = pokemon.getName();
                    mDbHelper.insertPokemonStore(mYelpId, pokemon.getName());
                }
                System.out.println(singleton.addSpecies(mCurrentPokemon));
                showPokemon();
            } else {
                Toast.makeText(DetailActivity.this, "Can't retrieve pokemon! Please try again!", Toast.LENGTH_SHORT).show();
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
            } catch (IllegalStateException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(BusinessDetail business) {
            super.onPostExecute(business);
            if (business!=null){
                mBusinessDetail = business;
                singleton.addBusiness(mBusinessDetail);
                setupViews(mBusinessDetail);
            } else {
                Toast.makeText(DetailActivity.this, "Business not found", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
