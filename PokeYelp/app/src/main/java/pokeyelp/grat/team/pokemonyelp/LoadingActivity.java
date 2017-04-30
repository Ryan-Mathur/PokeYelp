package pokeyelp.grat.team.pokemonyelp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pokeyelp.grat.team.pokemonyelp.activity_sample.SampleActivity;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.singleton.MrSingleton;

public class LoadingActivity extends AppCompatActivity {

    //Made an object to handle the asynctask as a member variable
    private GetYelpTokenTask mTokenRun;
    //These are our yelp app credentials

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        //Checks if there is connection
        //make sure we have permissions to use internet and check connection in our manifest, otherwise we can't do this
        NetworkInfo netInfo = ((ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()){

            //Made a new asynctask, and set it to the member variable I made at the top
            mTokenRun = new GetYelpTokenTask();

            //Because yelp need both our app id and app secret, I'm passing both to the AsyncTask
            //This also starts the AsyncTask, to see what this task does, look below at the class I made.
            mTokenRun.execute(Api.YELP_CLIENT_ID
                    ,Api.YELP_CLIENT_SECRET);
        }
        //if there isn't a connection, insult the user because he's broke
        else {
            Toast.makeText(this, "Get your broke ass some data.", Toast.LENGTH_SHORT).show();
        }

    }

    //this is a custom AsyncTask class I made just to handle getting a token, it takes in strings, because our credentials are strings
    //and it returns strings, because the token we are going to get is also a string

    //I chose to use AsyncTask instead of OkHttp's enque or java threads because, in the future, if we need to
    //do some UI stuff while we are getting the yelp token, like showing a progress bar or display a loading screen,
    //AsyncTask can do it better
    public class GetYelpTokenTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            //this crafts the request that we are sending to yelp,
            //first we make a OkHttp client, remember to add OkHttp3 as a dependency in our app
            OkHttpClient client = new OkHttpClient();

            //This is in yelp's documentation
            RequestBody niceBod = new FormBody.Builder()
                    .add("grant_type", "client_credentials")
                    .add("client_id", params[0])
                    .add("client_secret", params[1])
                    .build();

            //Now we are making the actual response
            Request request = new Request.Builder()
                    .url(Api.YELP_AUTH_URL)
                    .post(niceBod)
                    .build();

            //everything needs to be in a try catch
            try {
                //because we are doing it already in a backgroud thread, we can use OkHttp's execute method, otherwise we need to use enque
                //this gets a response object from yelp
                Response response = client.newCall(request).execute();

                //makes the response into a JSonOnject
                JSONObject tokenBlackGuy = new JSONObject(response.body().string());

                //here we return the actual access code, this triggers the onPostExecute method below
                return tokenBlackGuy.getString("access_token");
            }
            //these are all the possible exceptions, I didn't really do much with this except insult the user.
            catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(LoadingActivity.this, "I can't do this shit", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoadingActivity.this, "That's some bad J, son!", Toast.LENGTH_SHORT).show();
            }

            //returns a null value if something went wrong, like if an exception was thrown
            return null;
        }

        //This will run after we get the token from the doInBackground method
        @Override
        protected void onPostExecute(String accessToken) {
            super.onPostExecute(accessToken);
            //checks if the string is null, which could be possible if something went wrong with our request, or bad json, or anything
            if (accessToken != null) {

                //Launch a second activity and pass the token to the singleton
                Intent intent = new Intent(LoadingActivity.this, SampleActivity.class);
                MrSingleton.getInstance().setToken(accessToken);
                startActivity(intent);
                //once we move to the next activity, we don't need this loading screen anymore, so let's kill it.
                finish();

            } else {
                //if something went wrong, let's insult the user some more!
                Toast.makeText(LoadingActivity.this, "Something went wrong, what did you DO?!?!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
