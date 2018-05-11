package com.hackernews;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.hackernews.ui.NewsListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NewsListingActivityTest {

    @Rule
    public ActivityTestRule<NewsListActivity> rule = new ActivityTestRule<>(NewsListActivity.class);

    @Test
    public void shouldBeAbleToLoadNews() throws InterruptedException
    {
        Thread.sleep(5000);
        onView(withId(R.id.news_listing)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToPullToRefresh() throws InterruptedException
    {
        Thread.sleep(5000);
        onView(withId(R.id.news_listing)).check(matches(isDisplayed()));
        onView(withId(R.id.news_listing)).perform(swipeDown());
        Thread.sleep(5000);
        onView(withId(R.id.news_listing)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        Thread.sleep(5000);
        onView(withId(R.id.news_details_listing)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToViewNewsDetails() throws InterruptedException
    {
        Thread.sleep(3000);
        onView(withId(R.id.news_listing)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        Thread.sleep(5000);
        onView(withId(R.id.news_details_listing)).check(matches(isDisplayed()));
    }
}