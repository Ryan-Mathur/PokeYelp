package pokeyelp.grat.team.pokemonyelp.activity_collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import pokeyelp.grat.team.pokemonyelp.R;

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
