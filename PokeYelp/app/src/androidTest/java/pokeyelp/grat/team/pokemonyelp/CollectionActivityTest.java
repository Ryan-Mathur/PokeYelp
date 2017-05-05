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

    //user can move to the collection from the home screen and see pokemon caught.
    @Test
    public void testUserCanGoToCollection(){

        onView(withId(R.id.goto_collection_button)).perform(click());
        onView(withId(R.id.collection_recyclerview)).check(matches(isDisplayed()));
    }

    //user can move to the home screen from the collection activity
    @Test
    public void testUserCanGoToHomeFromCollection(){
        onView(withId(R.id.goto_collection_button)).perform(click());
        onView(withId(R.id.collection_recyclerview)).check(matches(isDisplayed()));
        onView(withId(R.id.goto_home_button)).perform(click());

    }
    //user can move to the search activity from the collection(){
    @Test
    public void testUserCanMoveToSearchActivity(){
        onView(withId(R.id.goto_collection_button)).perform(click());
        onView(withId(R.id.collection_recyclerview)).check(matches(isDisplayed()));
        onView(withId(R.id.go_to_search_button)).perform(click());
        onView(withId(R.id.search_by_name)).check(matches(isDisplayed()));
    }


}
