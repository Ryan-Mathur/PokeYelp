package pokeyelp.grat.team.pokemonyelp.helpers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Galen on 5/2/17.
 */

/*
Sample request

ShareOnSocialMedia.share(this, "something");

*/

public class ShareOnSocialMedia {

    public static void share(Context context, String giveMeWhatYouWantToShareInString) {


        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND );

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,giveMeWhatYouWantToShareInString);

        context.startActivity(intent);

    }
}
