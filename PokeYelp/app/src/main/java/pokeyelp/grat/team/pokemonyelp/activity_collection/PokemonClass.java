package pokeyelp.grat.team.pokemonyelp.activity_collection;

/**
 * Created by Admin on 5/1/17.
 */

public class PokemonClass {


    private String mPokemonName, mYelpId;
    private Boolean mBookmark;

    public PokemonClass( String pokemonName, String yelpId, Boolean bookmark) {
        mPokemonName = pokemonName;
        mYelpId = yelpId;
        mBookmark = bookmark;
    }



    public String getPokemonName() {
        return mPokemonName;
    }

    public void setPokemonName(String pokemonName) {
        mPokemonName = pokemonName;
    }

    public String getYelpId() {
        return mYelpId;
    }

    public void setYelpId(String yelpId) {
        mYelpId = yelpId;
    }

    public Boolean getBookmark() {
        return mBookmark;
    }

    public void setBookmark(Boolean bookmark) {
        mBookmark = bookmark;
    }
}
