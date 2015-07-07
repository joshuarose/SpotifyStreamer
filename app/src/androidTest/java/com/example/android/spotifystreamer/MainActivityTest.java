package com.example.android.spotifystreamer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kaaes.spotify.webapi.android.models.Artist;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by joshuarose on 7/4/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void appTitleIsShown() {
        onView(withText("Spotify Streamer")).check(matches(isDisplayed()));
    }

    @Test
    public void appMenuIsShown() {
        openContextualActionModeOverflowMenu();
        onView(withText(R.string.action_settings)).perform(click());
    }

    @Test
    public void artistSearchIsShown(){
        onView(withId(R.id.artist_search_text)).check(matches(isDisplayed()));
    }

    @Test
    public void artistNoResultsFound(){
        onView(withId(R.id.artist_search_text)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("FakeArtistPartBlorp"));
        onView(withText("No artists found")).check(matches(isDisplayed()));
    }

// This test always fails, can't find a way yet with espresso to provide enough time for the results to load
//    @Test
//    public void artistSearchYieldsResults(){
//        onView(withId(R.id.artist_search_text)).perform(click());
//        onView(withId(R.id.search_src_text)).perform(typeText("Coldplay"));
//        onData(allOf(is(instanceOf(Artist.class)), isArtistMatch("Coldplay")))
//                .inAdapterView(withId(R.id.artist_list_view))
//                .perform(click());
//        onView(withId(R.id.artist_text)).check(matches(isDisplayed()));
//    }

    public static Matcher<Artist> isArtistMatch(final String name){
        return new TypeSafeMatcher<Artist>() {
            boolean mFound;

            @Override
            public boolean matchesSafely(Artist tile) {
                // only match the first view :)
                if (mFound) return false;
                mFound = tile.name.equals(name);
                return mFound;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("expected "+ name);
            }
        };
    }
}
