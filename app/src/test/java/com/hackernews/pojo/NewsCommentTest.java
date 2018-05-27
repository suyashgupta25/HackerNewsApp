package com.hackernews.pojo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by suyashg on 27/05/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsCommentTest {

    @Mock
    NewsComment newsComment;

    @Test
    public void newsCommentObjCreation() {
        when(newsComment.getBy()).thenReturn("test1234");
        when(newsComment.getText()).thenReturn("text1234");

        newsComment.getBy();
        newsComment.getText();

        verify(newsComment, times(1)).getBy();
        verify(newsComment, times(1)).getText();
    }
}
