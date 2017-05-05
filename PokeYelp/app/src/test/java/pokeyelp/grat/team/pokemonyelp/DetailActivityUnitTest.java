package pokeyelp.grat.team.pokemonyelp;

import org.junit.Before;
import org.junit.Test;

import pokeyelp.grat.team.pokemonyelp.activity_collection.Store;

import static org.junit.Assert.*;

/**
 * Created by Admin on 5/5/17.
 */

public class DetailActivityUnitTest {

    private Store mStore;

    @Before
    public void setup(){
        mStore = new Store("pokemonName", "starbucks",false);

    }

    @Test
    public void pokemonNameStored(){

        String expected = "pokemonName";
        String actual = mStore.getPokemonName();
        assertEquals(expected, actual);

    }

    @Test
    public void yelpIdStored(){

        String expected = "starbucks";
        String actual = mStore.getYelpId();
        assertEquals(expected, actual);
    }

}
