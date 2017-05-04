package pokeyelp.grat.team.pokemonyelp.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import pokeyelp.grat.team.pokemonyelp.activity_collection.PokemonBusinessSQLiteOpenHelper;
import pokeyelp.grat.team.pokemonyelp.activity_home.HomeActivity;

/**
 * Created by Galen on 5/2/17.
 */

public class MyJobService extends JobService {
    public static final int NOTIFICATION_ID = 1;


    @Override
    public boolean onStartJob(JobParameters params) {

        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
        mBuilder.setContentText("Hi, The Pokemons are here again, time to catch!");



        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


        PokemonBusinessSQLiteOpenHelper mDBOpenHeloper = PokemonBusinessSQLiteOpenHelper.getInstance(getApplicationContext());
        mDBOpenHeloper.resetStores();


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }


}
