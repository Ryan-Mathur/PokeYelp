package pokeyelp.grat.team.pokemonyelp;

import org.junit.Before;

import pokeyelp.grat.team.pokemonyelp.activity_collection.Collection;

/**
 * Created by Admin on 5/4/17.
 */

public class CollectionUnitTest {

    private Collection collection;

    @Before
    public void setup(){
        mCollection = new Collection("pokemonName", 67658, "yelp_id");
    }


}
