package pokeyelp.grat.team.pokemonyelp.activity_sample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import pokeyelp.grat.team.pokemonyelp.R;

/**
 * Created by twang on 4/30/17.
 */

public class YelpView extends RecyclerView.ViewHolder{
    public TextView mName, mAddress, mType;

    public YelpView(View itemView) {
        super(itemView);
        mName = (TextView) itemView.findViewById(R.id.business_name);
        mAddress = (TextView) itemView.findViewById(R.id.business_address);
        mType = (TextView) itemView.findViewById(R.id.business_type);
    }

}
