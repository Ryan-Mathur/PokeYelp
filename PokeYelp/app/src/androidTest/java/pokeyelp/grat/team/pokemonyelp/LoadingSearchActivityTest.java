package pokeyelp.grat.team.pokemonyelp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoadingSearchActivityTest {

    @Rule
    public ActivityTestRule<LoadingActivity> mActivityTestRule = new ActivityTestRule<>(LoadingActivity.class);

    @Test
    public void loadingSearchActivityTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.search_button), withText("Search"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                withId(R.id.search_by_name));
        appCompatEditText.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                withId(R.id.the_search_button));
        appCompatButton2.perform(scrollTo(), click());


        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.search_by_name));
        appCompatEditText2.perform(scrollTo(), replaceText("bar"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.search_by_location));
        appCompatEditText3.perform(scrollTo(), replaceText("10019"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                withId(R.id.the_search_button));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                withId(R.id.filter));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText("Rating"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton5 = onView(
                withId(R.id.filter));
        appCompatButton5.perform(scrollTo(), click());



        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Best_match"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                1),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton7 = onView(
                withId(R.id.filter));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("Review_count"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                2),
                        isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatButton8 = onView(
                withId(R.id.filter));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Distance"),
                        childAtPosition(
                                allOf(withId(R.id.select_dialog_listview),
                                        withParent(withId(R.id.contentPanel))),
                                3),
                        isDisplayed()));
        appCompatTextView4.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.goto_collection_button), isDisplayed()));
        appCompatImageView.perform(click());

        pressBack();

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.goto_leaderboard_button), isDisplayed()));
        appCompatImageView2.perform(click());

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
