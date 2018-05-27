package com.hackernews.pojo;

import android.os.Parcel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by suyashg on 27/05/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsTest {

    @Mock
    Parcel parcel;

    @Test
    public void newsObjCreation() {
        News news = new News(9287489342L);
        news.setKids(Arrays.asList(9287489342L));

//        Parcel parcel = Parcel.obtain();
        news.writeToParcel(parcel, news.describeContents());
        parcel.setDataPosition(0);

        News newsFromParcel = News.CREATOR.createFromParcel(parcel);
        assertThat(news.getKids(), is(Arrays.asList(9287489342L)));
    }
}
