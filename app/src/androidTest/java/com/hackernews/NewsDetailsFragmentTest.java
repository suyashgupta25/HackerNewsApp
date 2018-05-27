package com.hackernews;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.hackernews.details.NewsDetailsFragment;
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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
@SuppressWarnings("deprecation")
public class NewsDetailsFragmentTest {

    @Rule
    public ActivityTestRule<NewsListActivity> rule = new ActivityTestRule<>(NewsListActivity.class, true, true);
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

    @Before
    public void setup(){
    }

    @Test
    public void shouldBeAbleToLoadDetailsNews() throws InterruptedException {
        News news = new News();
        news.setKids(new ArrayList<>(Arrays.asList(9287489342L)));
        NewsDetailsFragment newsDetailsFragment = NewsDetailsFragment.getInstance(news);
        rule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_news_container, newsDetailsFragment).addToBackStack(null).commit();
        onView(withId(R.id.news_details_listing)).check(matches(isDisplayed()));
    }

}