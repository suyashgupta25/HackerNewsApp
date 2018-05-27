package com.hackernews;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.hackernews.pojo.News;
import com.hackernews.ui.activities.NewsListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SuppressWarnings("deprecation")
public class NewsListingActivityTest {

    @Rule
    public ActivityTestRule<NewsListActivity> rule = new ActivityTestRule<>(NewsListActivity.class);
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = rule.getActivity().getNewsListIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    @Test
    public void shouldBeAbleToLoadNews() throws InterruptedException
    {
        onView(withId(R.id.news_listing)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToPullToRefresh() throws InterruptedException
    {
        onView(withId(R.id.news_listing)).check(matches(isDisplayed()));
        onView(withId(R.id.news_listing)).perform(swipeDown());
        onView(withId(R.id.news_listing)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.news_details_listing)).check(matches(isDisplayed()));
        Espresso.pressBack();
    }

    @Test
    public void shouldBeAbleToViewNewsDetails() throws InterruptedException
    {
        onView(withId(R.id.news_listing)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.news_details_listing)).check(matches(isDisplayed()));
        Espresso.pressBack();
        onView(withId(R.id.news_listing)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.news_listing)).perform(swipeUp());
        onView(withId(R.id.news_listing)).perform(swipeUp());
        Espresso.pressBack();
    }

    @Test
    public void shouldBeAbleScrollToBottom() throws InterruptedException
    {
        onView(withId(R.id.news_listing)).check(matches(isDisplayed()));
        onView(withId(R.id.news_listing)).perform(swipeUp());
        onView(withId(R.id.news_listing)).perform(swipeUp());
        onView(withId(R.id.news_listing)).perform(swipeUp());
    }

    @Test
    public void openNewsDetailsOnClickOfNews() throws InterruptedException
    {
        onView(withId(R.id.news_listing)).check(matches(isDisplayed()));
        News news = new News();
        news.setKids(new ArrayList<>(Arrays.asList(9287489342L)));
        rule.getActivity().onNewsClicked(news);
    }


}