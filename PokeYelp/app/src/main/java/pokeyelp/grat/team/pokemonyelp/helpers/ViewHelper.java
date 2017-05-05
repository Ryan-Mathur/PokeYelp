package pokeyelp.grat.team.pokemonyelp.helpers;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.BusinessDetail;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Location;

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

    public static String getAddress(Location location){
        String address = location.getAddress1();
        if (address == null || address.isEmpty() || address.equals("")){
            address = location.getAddress2();
        }
        if (address == null || address.isEmpty() || address.equals("")){
            address = location.getAddress3();
        }
        if (address == null || address.isEmpty() || address.equals("")){
            address = Api.YELP_NO_ADDRESS;
        }
        return address;
    }
}
