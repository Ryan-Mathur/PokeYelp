package pokeyelp.grat.team.pokemonyelp.activity_home;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.CollectionActivity;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchActivity;
import pokeyelp.grat.team.pokemonyelp.helpers.MyJobService;
import pokeyelp.grat.team.pokemonyelp.helpers.ToolBar;

public class HomeActivity extends AppCompatActivity {

    public static final int PERIODIC_JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        ToolBar.setupSimpleToolbar(findViewById(android.R.id.content));

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);




        JobInfo periodicJobInfo = new JobInfo.Builder(PERIODIC_JOB_ID,new ComponentName(this,MyJobService.class))
      //          .setPeriodic(86400000)    // this is the real timing for 24 hrs
                .setPeriodic(100)     //this for test the jobservice and notification
                .build();

        jobScheduler.schedule(periodicJobInfo);




        //Setup search button to take you from main page to search page
        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchActivity.class);
                v.getContext().startActivity(intent);


            }
        });

        Button collectionButton = (Button) findViewById(R.id.tocollection_activity);
        collectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CollectionActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }



}
