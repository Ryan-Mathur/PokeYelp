package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pokeyelp.grat.team.pokemonyelp.R;

/**
 * Created by Admin on 5/2/17.
 */

public class CollectionViewHolder extends RecyclerView.ViewHolder {

    private ImageView mCollectionImageView;
    private TextView mCollectionTextView;
    View mRootView;


    public CollectionViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView.findViewById(R.id.collection_rootview);
        mCollectionImageView = (ImageView) itemView.findViewById(R.id.collection_pokemon_image);
        mCollectionTextView = (TextView) itemView.findViewById(R.id.collection_company_textview);

    }
}
