package pokeyelp.grat.team.pokemonyelp.helpers;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Galen on 5/2/17.
 */

public class ShareOnSocialMedia {
    private Context mContext;
    private String mInput;

    public void share(Context context, String giveMeWhatYouWantToShareInString) {

        mContext=context;
        mInput=giveMeWhatYouWantToShareInString;

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND );

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,mInput);

        context.startActivity(intent);

    }
}
