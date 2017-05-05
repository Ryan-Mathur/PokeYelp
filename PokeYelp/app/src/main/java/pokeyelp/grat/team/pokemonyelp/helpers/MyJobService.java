package pokeyelp.grat.team.pokemonyelp.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.NotificationCompat;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.PokemonBusinessSQLiteOpenHelper;
import pokeyelp.grat.team.pokemonyelp.activity_home.HomeActivity;

/**
 * Created by Galen on 5/2/17.
 */

public class MyJobService extends JobService {
    public static final int NOTIFICATION_ID = 1;
    private static final String TAG = "MyJobService";


    @Override
    public boolean onStartJob(JobParameters params) {

        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.pokeball_icon);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        mBuilder.setContentTitle("Pokemon sighted!");
        mBuilder.setContentText("New pokemon have appeared!");

        SharedPreferences sp =getSharedPreferences(HomeActivity.SHAREPREFERENCE_KEY,MODE_PRIVATE);
        boolean isScheduled=sp.getBoolean(HomeActivity.SCHEDULE_KEY,false);

        if (isScheduled)
        {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            PokemonBusinessSQLiteOpenHelper mDBOpenHeloper = PokemonBusinessSQLiteOpenHelper.getInstance(getApplicationContext());
            mDBOpenHeloper.resetStores();

            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }else {
            sp.edit().putBoolean(HomeActivity.SCHEDULE_KEY,true).commit();

        }

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
