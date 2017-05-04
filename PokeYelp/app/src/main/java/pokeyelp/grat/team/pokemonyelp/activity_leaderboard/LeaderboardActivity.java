package pokeyelp.grat.team.pokemonyelp.activity_leaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.CollectionActivity;
import pokeyelp.grat.team.pokemonyelp.activity_collection.PokemonBusinessSQLiteOpenHelper;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchActivity;
import pokeyelp.grat.team.pokemonyelp.helpers.ToolBar;

public class LeaderboardActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private static final String LEADERBOARD_ID = "CgkI1IS6tJcSEAIQAg";
    private static final int REQUEST_LEADERBOARD = 1;
    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 9001;
    private boolean mResolvingConnectionFailure;
    private boolean mSignInClicked;
    private SignInButton mSignInButton;
    private Button mShowLeaderBoardButton;
    private boolean mAutoStartSignInFlow;

//please note that some of the methods used here is following googles guidelines for setting up google games

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Get references to views
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mShowLeaderBoardButton = (Button) findViewById(R.id.show_leaderboard_button);


        //reference to view for toolbar
        ToolBar.setupSimpleToolbar(findViewById(android.R.id.content));


        //Set those views to onclick listener
        mSignInButton.setOnClickListener(this);
        mShowLeaderBoardButton.setOnClickListener(this);

        //look for api connection if none then create
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Games.API)
                    .addScope(Games.SCOPE_GAMES)
                    .build();
        }

    }

    //do something when those buttons are clicked
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signInClicked();
                break;
            case R.id.show_leaderboard_button:
                showLeaderBoardClicked();
                break;
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;

        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInFlow) {
            mAutoStartSignInFlow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, (R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
        }

        // Put code here to display the sign-in button
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;
            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();
            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_failure);
            }
        }

    }


    private void signInClicked() {
        //click sign in for google services
        mSignInClicked = true;
        mGoogleApiClient.connect();
        Toast.makeText(this, "You have signed in!", Toast.LENGTH_SHORT).show();
    }


    private void showLeaderBoardClicked() {

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            //add scores based on pokemon caught to leaderboard
            int pokemonCaptured = PokemonBusinessSQLiteOpenHelper.getInstance(LeaderboardActivity.this).getYourCollection().size();
            Games.Leaderboards.submitScore(mGoogleApiClient, LEADERBOARD_ID, pokemonCaptured);
            //this is what actually shows the leaderboard
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient,
                    LEADERBOARD_ID), REQUEST_LEADERBOARD);
        } else {

            // Alternative implementation (or warn user that they must
            // sign in to use this feature)

            Toast.makeText(this, "You must sign in to use this feature!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
}
