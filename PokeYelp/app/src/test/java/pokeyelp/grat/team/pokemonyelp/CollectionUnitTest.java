package pokeyelp.grat.team.pokemonyelp;

import org.junit.Before;
import org.junit.Test;

import pokeyelp.grat.team.pokemonyelp.activity_collection.Collection;
import static org.junit.Assert.*;

/**
 * Created by Admin on 5/4/17.
 */

public class CollectionUnitTest {

    private Collection collection;

    /*The Collection activity stores the pokemon name, the pokemon id, and yelp store id.
    This information is passed from Capture activity to the DB where it is displayed in the
    collection activity. This test will check that, that information is entered then displayed
    correctly. */

    @Before
    public void setup(){
        collection = new Collection("pokemonName", 67658, "yelp_id");
        
    }
    
    @Test
    public void checkPokemonName(){

        String expected = "pokemonName";
        String actual = collection.getPokemonName();
        assertEquals(expected, actual);
        
    }


    @Test
    public void checkPokemonId(){

        int expected = 67658;
        int actual = collection.getId();
        assertEquals(expected, actual);
        
        
    }
        
    @Test
    public void checkYelpId(){
        String expected = "yelp_id";
        String actual = collection.getYelpStore();
        assertEquals(expected, actual);
        
    }
}
