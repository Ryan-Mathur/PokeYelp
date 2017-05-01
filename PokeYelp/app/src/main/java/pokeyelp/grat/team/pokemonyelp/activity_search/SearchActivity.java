package pokeyelp.grat.team.pokemonyelp.activity_search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class SearchActivity extends AppCompatActivity {

    private String mYelpToken;

    private GetYelpBusinesses mGetYelp;
    //recycler adapter here because we need to call it inside the asynctask to update it's list
    private SearchViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mYelpToken = MrSingleton.getInstance().getToken();

        final EditText searchByNameInput= (EditText) findViewById(R.id.search_by_name);
        final EditText searchByLoacationInput= (EditText) findViewById(R.id.search_by_location);
        Button search_button = (Button) findViewById(R.id.the_search_button);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String location= searchByLoacationInput.getText().toString();
                String categories=searchByNameInput.getText().toString();
                mGetYelp = new GetYelpBusinesses();

                //this query can be changed to anything we want
                //Right now it's just doing a query for every business near General Assembly
                mGetYelp.execute(Api.YELP_BASE_URL +
                        "businesses/search?"+"location="+location+
                        "&&term="+categories+
                        "&&sort_by=rating");
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
                mAdapter.giveNewSearchList(apiResult.getBusinesses());
            }

        }
    }


}
