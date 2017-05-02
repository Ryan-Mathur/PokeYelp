package pokeyelp.grat.team.pokemonyelp.activity_sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Business;

/**
 * Created by twang on 4/30/17.
 */

public class YelpRecyclerAdapter extends RecyclerView.Adapter<YelpView> {
    private List<Business> mBusinesses;

    public YelpRecyclerAdapter(List<Business> businesses) {
        mBusinesses = businesses;
    }

    @Override
    public YelpView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new YelpView(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_yelp, parent, false));
    }

    @Override
    public void onBindViewHolder(YelpView holder, int position) {
        Business currentBusiness = mBusinesses.get(position);
        holder.mName.setText(currentBusiness.getName());
        holder.mAddress.setText(currentBusiness.getLocation().getAddress1());
        holder.mType.setText(currentBusiness.getCategories().get(0).getTitle());
    }

    @Override
    public int getItemCount() {
        return mBusinesses.size();
    }

    public void giveNewList(List<Business> newList){
        mBusinesses = newList;
        notifyDataSetChanged();
    }

}