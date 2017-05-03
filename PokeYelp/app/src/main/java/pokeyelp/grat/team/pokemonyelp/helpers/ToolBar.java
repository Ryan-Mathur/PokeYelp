package pokeyelp.grat.team.pokemonyelp.helpers;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_collection.CollectionActivity;
import pokeyelp.grat.team.pokemonyelp.activity_home.HomeActivity;
import pokeyelp.grat.team.pokemonyelp.activity_leaderboard.LeaderboardActivity;
import pokeyelp.grat.team.pokemonyelp.activity_search.SearchActivity;

/**
 * Created by Galen on 5/2/17.
 */


/**
 * in your activity you just need to add one line of codes
 * <p>
 * "   ###  ToolBar.setupSimpleToolbar(findViewById(android.R.id.content));   ###"
 * <p>
 * you also need to inset the following code in your layout
 * <p>
 * ####  <include layout="@layout/toolbar_layout" />    #####
 */


public class ToolBar {
    public static void setupSimpleToolbar(View view) {


        TextView home_button = (TextView) view.findViewById(R.id.goto_home_button);
        ImageView search_button = (ImageView) view.findViewById(R.id.go_to_search_button);
        ImageView collection_button = (ImageView) view.findViewById(R.id.goto_collection_button);
        ImageView leaderboard_button = (ImageView) view.findViewById(R.id.goto_leaderboard_button);

        if (view.getContext() instanceof HomeActivity) {
            search_button.setVisibility(View.GONE);
        }

        if (view.getContext() instanceof SearchActivity) {
            search_button.setVisibility(View.GONE);
        }
        if (view.getContext() instanceof CollectionActivity) {
            collection_button.setVisibility(View.GONE);
        }
        if (view.getContext() instanceof LeaderboardActivity) {
            leaderboard_button.setVisibility(View.GONE);
        }


        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext() instanceof HomeActivity) {

                } else {
                    v.getContext().startActivity(new Intent(v.getContext(), HomeActivity.class));
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), SearchActivity.class));

            }
        });

        collection_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), CollectionActivity.class));

            }
        });

        leaderboard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), LeaderboardActivity.class));

            }
        });

    }


}
