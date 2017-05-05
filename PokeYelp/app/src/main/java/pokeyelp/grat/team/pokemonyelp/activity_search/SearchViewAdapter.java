package pokeyelp.grat.team.pokemonyelp.activity_search;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import pokeyelp.grat.team.pokemonyelp.R;
import pokeyelp.grat.team.pokemonyelp.activity_detail.DetailActivity;
import pokeyelp.grat.team.pokemonyelp.constants.Api;
import pokeyelp.grat.team.pokemonyelp.constants.IntentCode;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.Business;
import pokeyelp.grat.team.pokemonyelp.helpers.ViewHelper;

/**
 * Created by Galen on 5/1/17.
 */

public class SearchViewAdapter extends RecyclerView.Adapter<SearchViewHolder> {
    private List<Business> mBusinesses;

    public SearchViewAdapter(List<Business> businesses) {

        mBusinesses = businesses;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new SearchViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_custom_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final Business currentBusiness = mBusinesses.get(position);

        holder.mCompanyName.setText(currentBusiness.getName());
        String address= ViewHelper.getAddress(currentBusiness.getLocation());
        if (address.equals(Api.YELP_NO_ADDRESS)){
            address = "";
        } else {
            address += ", ";
        }
        holder.mAddress.setText(address + currentBusiness.getLocation().getCity());
        holder.mReview.setText(currentBusiness.getReviewCount()+ " Reviews");

        if (!currentBusiness.getImageUrl().isEmpty()) {
            Picasso.with(holder.mBusinessPhoto.getContext()).load(currentBusiness.getImageUrl())
                    .resize(200, 200).into(holder.mBusinessPhoto);
        } else {
            holder.mBusinessPhoto.setImageResource(R.drawable.box);
        }

        //these are placeholders for now
        holder.mYelpLogo.setImageResource(R.drawable.yelp_trademark_rgb_outline);
        ViewHelper.setStars(currentBusiness.getRating(), holder.mRatingView);
        holder.mCategory.setText(currentBusiness.getCategories().get(0).getTitle());

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),DetailActivity.class);
                intent.putExtra(IntentCode.BUSINESS_ID_TO_DETAILS,currentBusiness.getId());
                v.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {

        return mBusinesses.size();
    }


    public void giveNewSearchList(List<Business> newList){
        mBusinesses = newList;
        notifyDataSetChanged();
    }
}
