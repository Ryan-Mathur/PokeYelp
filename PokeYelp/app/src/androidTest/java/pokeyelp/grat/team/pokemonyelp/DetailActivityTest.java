package pokeyelp.grat.team.pokemonyelp;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import pokeyelp.grat.team.pokemonyelp.activity_home.HomeActivity;
import android.support.test.espresso.contrib.RecyclerViewActions;

/**
 * Created by Admin on 5/4/17.
 */

public class DetailActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    //Detail view for business is viewable
    @Test
    public void testDetailViewVisible(){
        //Detail view for business is viewable
        onView(withId(R.id.search_button)).perform(click());
        onView(withId(R.id.search_by_name)).perform(typeText("General Assembly"));
        onView(withId(R.id.the_search_button)).perform(click());
        onView(withId(R.id.search_result_recyvler_View)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
        onView(withId(R.id.detail_name)).check(matches(isDisplayed()));
    }
    //user can scan for pokemon
    /*Test does not check if a pokemon shows as sometimes it is necessary to hit
    the button multiple times for an image to display*/
    @Test
    public void testUserCanScanForPokemon(){
        onView(withId(R.id.search_button)).perform(click());
        onView(withId(R.id.search_by_name)).perform(typeText("General Assembly"));
        onView(withId(R.id.the_search_button)).perform(click());
        onView(withId(R.id.search_result_recyvler_View)).perform(RecyclerViewActions
                .actionOnItemAtPosition(1, click()));
        onView(withId(R.id.detail_name)).check(matches(isDisplayed()));
        onView(withId(R.id.detail_button_scan_pokemon)).perform(click());

    }


}
