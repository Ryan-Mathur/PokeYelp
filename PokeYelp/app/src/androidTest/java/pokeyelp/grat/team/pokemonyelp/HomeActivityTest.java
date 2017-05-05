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

public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    //move to search screen from home activity
    @Test
    public void testMoveToSeachActivity(){
        onView(withId(R.id.search_button)).perform(click());
        onView(withId(R.id.search_linerlayout_view)).check(matches(isDisplayed()));

    }

    //move to leader board activity from home activity
    @Test
    public void testMovetoLeaderboardActivity(){
        onView(withId(R.id.goto_leaderboard_button)).perform(click());
    }

    //move to collection activity from home activity
    @Test
    public void testMovetoCollectionActivity(){

        onView(withId(R.id.goto_collection_button)).perform(click());

    }
}
