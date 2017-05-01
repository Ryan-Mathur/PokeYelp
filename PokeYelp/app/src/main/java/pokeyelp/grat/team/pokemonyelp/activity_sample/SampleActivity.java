package pokeyelp.grat.team.pokemonyelp.activity_sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

public class SampleActivity extends AppCompatActivity {

    //making the token a member variable so every function can access this
    private String mYelpToken;
    //another asynctask that handles making the api call
    private GetYelpBusinesses mGetYelp;
    //recycler adapter here because we need to call it inside the asynctask to update it's list
    private YelpRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        //getting the token from singleton
        mYelpToken = MrSingleton.getInstance().getToken();

        //starting the async task of getting businesses
        mGetYelp = new GetYelpBusinesses();

        //this query can be changed to anything we want
        //Right now it's just doing a query for every business near General Assembly
        mGetYelp.execute(Api.YELP_BASE_URL + "businesses/search?latitude=40.740108&longitude=-73.990196");

        //setting up the recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.yelp_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        //because we are running an asynctask in the background, we don't know WHEN we are going to get
        //the list to give to the recycler adapter. So here, I'm just giving it an empty list, as a placeholder.
        mAdapter = new YelpRecyclerAdapter(new ArrayList<Business>());

        //more setup for the recycler view
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }

    //the asynctask class that handles getting yelp businesses. It takes in a url query as a string
    //and returns a ApiResult class that I made to work with GSON classes
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
                //starts the onPostExecute method, and gives it the ApiResult object
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
                mAdapter.giveNewList(apiResult.getBusinesses());
            }

        }
    }
}