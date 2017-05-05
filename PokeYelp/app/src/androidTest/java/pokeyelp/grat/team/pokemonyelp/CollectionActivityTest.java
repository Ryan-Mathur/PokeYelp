package pokeyelp.grat.team.pokemonyelp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import pokeyelp.grat.team.pokemonyelp.activity_home.HomeActivity;

/**
 * Created by Admin on 5/4/17.
 */

public class CollectionActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Test
    public void testUserCanGoToCollection(){

        onView(withId(R.id.goto_collection_button)).perform(click());

        onView(withId(R.id.collection_recyclerview)).check(matches(isDisplayed()));
    }


}
