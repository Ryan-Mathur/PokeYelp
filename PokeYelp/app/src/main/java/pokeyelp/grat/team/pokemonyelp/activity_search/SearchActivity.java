package pokeyelp.grat.team.pokemonyelp.activity_search;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.ApiResult;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Business;
import pokeyelp.grat.team.pokemonyelp.singleton.MrSingleton;

public class SearchActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final String TAG = "SearchActivity";

    public static final int REQUEST_LOCATION = 10;
    private GoogleApiClient mGoogleApiClient;
    private String mLong, mLat;
    private String mYelpToken;
    private Button mFilterButton;
    private String mSortBy;
    private String mloaction,mcategories;


    private GetYelpBusinesses mGetYelp;
    //recycler adapter here because we need to call it inside the asynctask to update it's list
    private SearchViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mYelpToken = MrSingleton.getInstance().getToken();

        final EditText searchByNameInput = (EditText) findViewById(R.id.search_by_name);
        final EditText searchByLoacationInput = (EditText) findViewById(R.id.search_by_location);

        //when i type jvftj, dones't show anything, when i type kui, the app crushed!

        Button search_button = (Button) findViewById(R.id.the_search_button);
        mFilterButton= (Button) findViewById(R.id.filter);


        // call a api from yelp when click on the search button
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mloaction = searchByLoacationInput.getText().toString();

                mcategories = searchByNameInput.getText().toString();

                mGetYelp = new GetYelpBusinesses();

                // to check, if the location is empty, it return the current location business.
                // if it is not empty, it will get the business as the user required

                if(mloaction.isEmpty()||mloaction==""){

                    mGetYelp.execute(Api.YELP_BASE_URL +
                            "businesses/search?latitude="+mLat+
                            "&longitude="+mLong+
                            "&&term=" + mcategories +
                            "&&sort_by=best_match");

                    Log.d(TAG, "onClick: mlong: "+mLong+" mylat: "+ mLat);

                }

                else {

                    //this query is based on the users' input,
                    mGetYelp.execute(Api.YELP_BASE_URL +
                            "businesses/search?" + "location=" + mloaction +
                            "&&term=" + mcategories +
                            "&&sort_by=best_match");
                }

                    //setting up the recycler view
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_result_recyvler_View);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);

                    //because we are running an asynctask in the background, we don't know WHEN we are going to get
                    //the list to give to the recycler adapter. So here, I'm just giving it an empty list, as a placeholder.
                    mAdapter = new SearchViewAdapter(new ArrayList<Business>());

                    //more setup for the recycler view
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(mAdapter);



            }
        });




        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        // filter the list by click the filter button

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortTheSerachResultList();
                Toast.makeText(SearchActivity.this, "sort by!!!", Toast.LENGTH_SHORT).show();
            }
        });





    }





    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }

        else {

            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (lastLocation == null) {

                LocationRequest locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
            }
            else {
                mLat=String.valueOf(lastLocation.getLatitude());
                mLong=String.valueOf(lastLocation.getLongitude());

            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                                mGoogleApiClient);

                        if (lastLocation == null) {
                            LocationRequest locationRequest = LocationRequest.create()
                                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
                        }



                        break;
                    }
                }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public class GetYelpBusinesses extends AsyncTask<String, Void, ApiResult> {

        @Override
        protected ApiResult doInBackground(String... urlQuery) {
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
                ApiResult results = gson.fromJson(response.body().string(), ApiResult.class);

                // if the user type something unvaild, the  results.getBusinesses().size() will return an NullPointerException, and will ask the user to type something vaild.
                results.getBusinesses().size();
                //starts the onPostExecute method, and gives it the ApiResult object
                return results;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ApiResult apiResult) {
            super.onPostExecute(apiResult);
            if (apiResult != null) {

                //because we now have a real list of businesses to give to the recycler adapter, we
                //are replacing its current list with this new list that we got from our API call.

                mAdapter.giveNewSearchList(apiResult.getBusinesses());

                //make the filter button visible after search result showed.

                mFilterButton.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(SearchActivity.this, "Please type something valid", Toast.LENGTH_SHORT).show();
            }

        }
    }



   // make a method to sort the list
    private Dialog SortTheSerachResultList(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Sort the results by one of the these modes: best_match, rating, review_count or distance. By default it's best_match.
        String strArry[]= {"Rating","Best_match","Review_count","Distance"};

        builder.setTitle("Sort By")
                .setItems(strArry, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //// The 'which' argument contains the index position
                        switch (which){
                            case 0:
                                mSortBy="rating";

                                break;
                            case 1:
                                mSortBy="best_match";

                                break;
                            case 2:
                                mSortBy="review_count";

                                break;
                            case 3:
                                mSortBy="distance";
                                break;
                            default:
                                mSortBy="best_match";
                        }


                        if(mloaction.isEmpty()||mloaction==""){

                           new GetYelpBusinesses().execute(Api.YELP_BASE_URL +
                                    "businesses/search?latitude="+mLat+
                                    "&longitude="+mLong+
                                    "&&term=" + mcategories +
                                    "&&sort_by="+mSortBy);
                        }

                        else {

                            //this query is based on the users' input,
                           new GetYelpBusinesses().execute(Api.YELP_BASE_URL +
                                    "businesses/search?" + "location=" + mloaction +
                                    "&&term=" + mcategories +
                                    "&&sort_by="+mSortBy);
                        }


                    }
                });


        AlertDialog dialog = builder.create();
        dialog.show();

        return dialog;


    }

}
