package pokeyelp.grat.team.pokemonyelp.activity_collection;

/**
 * Created by Admin on 5/1/17.
 */

public class Collection {

    private Long mId;
    private String mPokemonName, mYelpStore;

    public Collection(String pokemonName, String yelpStore) {
        mPokemonName = pokemonName;
        mYelpStore = yelpStore;
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
