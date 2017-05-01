package pokeyelp.grat.team.pokemonyelp.activity_home;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pokeyelp.grat.team.pokemonyelp.LoadingActivity;
import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchActivity;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchViewAdapter;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.ApiResult;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Business;
import pokeyelp.grat.team.pokemonyelp.singleton.MrSingleton;

public class HomeActivity extends AppCompatActivity {
    private String mGetYelpTokenMain;
    //recycler adapter here because we need to call it inside the asynctask to update it's list
    private SearchViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check internet connection status
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(HomeActivity.this, "Network is active", Toast.LENGTH_SHORT).show();

            //Get Token needed for yelp header call
            mGetYelpTokenMain = MrSingleton.getInstance().getToken();

            //Start async task of getting businesses
            //// TODO: get current location for query
            yelpBusinessesForMain yelpBusinessesForMain = new yelpBusinessesForMain();
            yelpBusinessesForMain.execute(Api.YELP_BASE_URL + "businesses/search?latitude=40.740108&longitude=-73.990196&limit=10");


        } else {
            Toast.makeText(this, R.string.check_network, Toast.LENGTH_LONG).show();
        }


        //Setup search button to take you from main page to search page
        setContentView(R.layout.activity_home);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        //setting up the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.home_activity_recycler_View);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);

        //because we are running an asynctask in the background, we don't know WHEN we are going to get
        //the list to give to the recycler adapter. So here, I'm just giving it an empty list, as a placeholder.
        mAdapter = new SearchViewAdapter(new ArrayList<Business>());

        //more setup for the recycler view
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }

    //Call yelp to get list of 10 businesses

    public class yelpBusinessesForMain extends AsyncTask<String, Void, ApiResult> {
        private static final String TAG = "yelpBusinessesForMain";

        public yelpBusinessesForMain() {

        }

        @Override
        protected ApiResult doInBackground(String... urlQuery) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()

                    //yelp requires the header to have the token here for authentication
                    .header("Authorization", "Bearer " + mGetYelpTokenMain)

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

                //log results to make sure getting correct output
                Log.d(TAG, "doInBackground: " + results.getBusinesses().get(1).getName());

                return results;



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ApiResult apiResult) {
            super.onPostExecute(apiResult);
            if (apiResult!=null){
                //because we now have a real list of businesses to give to the recycler adapter, we
                //are replacing its current list with this new list that we got from our API call.
                mAdapter.giveNewSearchList(apiResult.getBusinesses());
            } else {
                Toast.makeText(HomeActivity.this, "Please type something valid", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
