package pokeyelp.grat.team.pokemonyelp.helpers;

import android.widget.ImageView;

import pokeyelp.grat.team.pokemonyelp.R;

/**
 * Created by twang on 5/4/17.
 */

public class ViewHelper {

    public static void setStars(double rating, ImageView starView){
        if (rating >=0 && rating <1){
            starView.setImageResource(R.drawable.stars_extra_large_0);
        } else if (rating >=1&& rating < 1.5){
            starView.setImageResource(R.drawable.stars_extra_large_1);
        } else if (rating >=1.5&& rating < 2){
            starView.setImageResource(R.drawable.stars_extra_large_1_half);
        } else if (rating >=2&& rating < 2.5){
            starView.setImageResource(R.drawable.stars_extra_large_2);
        } else if (rating >=2.5&& rating < 3){
            starView.setImageResource(R.drawable.stars_extra_large_2_half);
        } else if (rating >=3&& rating < 3.5){
            starView.setImageResource(R.drawable.stars_extra_large_3);
        } else if (rating >=3.5&& rating < 4){
            starView.setImageResource(R.drawable.stars_extra_large_3_half);
        } else if (rating >=4&& rating < 4.5){
            starView.setImageResource(R.drawable.stars_extra_large_4);
        } else if (rating >=4.5&& rating < 5){
            starView.setImageResource(R.drawable.stars_extra_large_4_half);
        } else {
            starView.setImageResource(R.drawable.stars_extra_large_5);
        }

    }
}
