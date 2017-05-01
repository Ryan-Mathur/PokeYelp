package pokeyelp.grat.team.pokemonyelp.activity_collection;

/**
 * Created by Admin on 5/1/17.
 */

public class PokemonClass {

    private long mId;
    private String mPokemonName, mYelpId, mBookmark;

    public PokemonClass(Long id, String pokemonName, String yelpId, String bookmark) {
        mId = id;
        mPokemonName = pokemonName;
        mYelpId = yelpId;
        mBookmark = bookmark;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
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

    public String getBookmark() {
        return mBookmark;
    }

    public void setBookmark(String bookmark) {
        mBookmark = bookmark;
    }
}
