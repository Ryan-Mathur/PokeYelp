package pokeyelp.grat.team.pokemonyelp.activity_collection;

/**
 * Created by Admin on 5/1/17.
 */

public class Collection {

    private int mId;
    private String mPokemonName, mYelpStore;

    public Collection(String pokemonName, int id, String yelpStore) {
        mPokemonName = pokemonName;
        mId = id;
        mYelpStore = yelpStore;
    }

    public int getId() {
        return mId;
    }

    public String getPokemonName() {
        return mPokemonName;
    }

    public void setPokemonName(String pokemonName) {
        mPokemonName = pokemonName;
    }

    public String getYelpStore() {
        return mYelpStore;
    }

    public void setYelpStore(String yelpStore) {
        mYelpStore = yelpStore;
    }
}
