package pokeyelp.grat.team.pokemonyelp.activity_search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pokeyelp.grat.team.pokemonyelp.R;

/**
 * Created by Galen on 5/1/17.
 */

public class SearchViewHolder extends RecyclerView.ViewHolder {

    public TextView mCompanyName, mAddress, mReview, mCategory;
    public ImageView mBusinessPhoto, mRatingView, mYelpLogo;
    View mRootView;

    public SearchViewHolder(View itemView) {
        super(itemView);
        mCompanyName = (TextView) itemView.findViewById(R.id.search_business_name);
        mAddress = (TextView) itemView.findViewById(R.id.search_address);
        mReview = (TextView) itemView.findViewById(R.id.search_numbofreviews);
        mCategory = (TextView) itemView.findViewById(R.id.search_category);
        mBusinessPhoto= (ImageView) itemView.findViewById(R.id.search_imageview);
        mRatingView = (ImageView) itemView.findViewById(R.id.search_fivestars);
        mYelpLogo= (ImageView) itemView.findViewById(R.id.search_yelplogo);
        mRootView=itemView;
    }
}
