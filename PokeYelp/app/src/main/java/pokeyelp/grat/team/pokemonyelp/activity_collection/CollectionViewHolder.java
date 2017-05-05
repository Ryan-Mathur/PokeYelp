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

    public ImageView mCollectionImageView;
    public TextView mCollectionPokemonName;
    View mRootView;


    public CollectionViewHolder(View itemView) {
        super(itemView);
        mRootView = itemView;
        mCollectionImageView = (ImageView) itemView.findViewById(R.id.collection_pokemon_image);
        mCollectionPokemonName = (TextView) itemView.findViewById(R.id.collection_pokemonName_textview);

    }
}
