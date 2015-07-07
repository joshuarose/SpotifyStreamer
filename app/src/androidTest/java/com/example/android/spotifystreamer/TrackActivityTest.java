package com.example.android.spotifystreamer;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by joshuarose on 7/6/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TrackActivityTest {

    @Rule
    public ActivityTestRule<TrackActivity> mActivityRule = new ActivityTestRule(TrackActivity.class);

    @Test
    public void actionBarShowsTitle(){
//        onView(withText("Top 10 Tracks")).check(matches(isDisplayed()));
    }
}
