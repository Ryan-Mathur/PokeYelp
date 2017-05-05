package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;
import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.constants.Pokemon;

/**
 * Created by Admin on 5/2/17.
 */

public class CollectionRecyclerViewAdapater extends RecyclerView.Adapter<CollectionViewHolder> {

    private List<Collection> mCollectionList;

    public CollectionRecyclerViewAdapater(List<Collection> collectionList) {
        mCollectionList = collectionList;
    }

    @Override
    public CollectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CollectionViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.collection_custom_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(CollectionViewHolder holder, int position) {
        final Collection currentCollection = mCollectionList.get(position);
        //image for the pokemon
        String imageUrl = Pokemon.POKEMON_SPRITE_BASE_URL + currentCollection.getPokemonName() + ".gif";
        Picasso.with(holder.mCollectionImageView.getContext()).load(imageUrl).into(holder.mCollectionImageView);

        String displayName = currentCollection.getId() + ": " + currentCollection.getPokemonName().substring(0, 1).toUpperCase() + currentCollection.getPokemonName().substring(1);
        holder.mCollectionPokemonName.setText(displayName);
    }

    @Override
    public int getItemCount() {
        return mCollectionList.size();
    }

    public void getnewCollection(List<Collection> newCollection){
        mCollectionList = newCollection;
        notifyDataSetChanged();
    }

}
