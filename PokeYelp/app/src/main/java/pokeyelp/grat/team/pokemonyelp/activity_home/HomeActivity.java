package pokeyelp.grat.team.pokemonyelp.activity_home;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.CollectionActivity;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchActivity;
import pokeyelp.grat.team.pokemonyelp.constants.GameSettings;
import pokeyelp.grat.team.pokemonyelp.helpers.MyJobService;
import pokeyelp.grat.team.pokemonyelp.helpers.ToolBar;

public class HomeActivity extends AppCompatActivity {


    public static final int PERIODIC_JOB_ID = 1;

    private static final String TAG = "HomeActivity";
    public static final String SHAREPREFERENCE_KEY = "SHARESHARE_KEY";
    public static final String SCHEDULE_KEY = "keykeykye";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ToolBar.setupSimpleToolbar(findViewById(android.R.id.content));


        //Setup search button to take you from main page to search page
        findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int size = jobScheduler.getAllPendingJobs().size();

        if (size == 0) {


            SharedPreferences sp = getSharedPreferences(SHAREPREFERENCE_KEY, MODE_PRIVATE);
            sp.edit().putBoolean(SCHEDULE_KEY, false)
                    .commit();


            JobInfo periodicJobInfo = new JobInfo.Builder(PERIODIC_JOB_ID, new ComponentName(this, MyJobService.class))
                    .setPeriodic(GameSettings.STORE_RESET_TIME)
                    .build();


            jobScheduler.schedule(periodicJobInfo);

        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBg();
    }

    public void setupBg(){
        ImageView bgPic = (ImageView) findViewById(R.id.home_bgpic);

        int randPic = (int) (Math.random()*3);

        switch (randPic){
            case 0:
                bgPic.setImageResource(R.drawable.poster_pallet);
                break;
            case 1:
                bgPic.setImageResource(R.drawable.poster_cerulean);
                break;
            case 2:
                bgPic.setImageResource(R.drawable.poster_saffron);
                break;
            case 3:
                bgPic.setImageResource(R.drawable.poster_cinnabar);
                break;
            default:
                bgPic.setImageResource(R.drawable.poster_pallet);
                break;
        }
    }


}
