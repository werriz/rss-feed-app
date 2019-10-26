package com.jurijz.rssfeed;

import com.jurijz.rssfeed.client.RssFeedViewController;
import com.jurijz.rssfeed.domain.RssFeed;
import com.jurijz.rssfeed.service.RssFeedRepository;
import com.jurijz.rssfeed.service.RssFeedService;
import com.jurijz.rssfeed.service.RssFeedServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

/**
 * Created by jurijz on 10/6/2018.
 */
public class RssFeedTest {

    @InjectMocks
    private RssFeedViewController controller;
    @Mock
    private RssFeedRepository repository;
    private RssFeed rssFeed;
    private BindingResult bindingResult;

    private static final String TEST_URL = "https://www.15min.lt/rss";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        rssFeed = new RssFeed();
        bindingResult = new MapBindingResult(new HashMap<>(), "rssFeed");

        RssFeedService service = new RssFeedServiceImpl(repository);
        controller = new RssFeedViewController(service);
    }

    @Test
    public void rssFeed_hasValidUrlAndName() {
        rssFeed.setUrl(TEST_URL);
        rssFeed.setFeedName("testName");

        String result = controller.rssFeedSubmit(rssFeed, bindingResult);

        Assert.assertTrue("Controllers method rssFeedSubmit should return 'redirect:/'" ,
                !result.isEmpty() && result.equals("redirect:/"));
        Assert.assertFalse("RssFeed title should be filled",
                rssFeed.getTitle().isEmpty());

        Mockito.verify(repository).save(rssFeed);
    }

    @Test
    public void rssFeed_hasInvalidUrl() {
        rssFeed.setUrl("invalidUrl");

        String result = controller.rssFeedSubmit(rssFeed,
                bindingResult);

        Assert.assertTrue("Controllers method rssFeedSubmit should return 'addFeed'" ,
                !result.isEmpty() && result.equals("addFeed"));
        Assert.assertTrue("Binding result should have errors",
                bindingResult.hasErrors());

        Mockito.verify(repository, never()).save(rssFeed);
    }

    @Test
    public void viewRssFeeds() {
        Mockito.when(repository.findAll())
                .thenReturn(Collections.singletonList(rssFeed));
        String result = controller.viewFeeds(new ExtendedModelMap());

        Assert.assertTrue("Controller should return 'viewFeeds'",
                !result.isEmpty() && result.equals("viewFeeds"));


        Mockito.verify(repository, atLeastOnce()).findAll();
        Mockito.validateMockitoUsage();
    }

    @Test
    public void viewSingleRssFeed() {
        rssFeed.setId(1);
        Mockito.when(repository.findById(1))
                .thenReturn(Optional.of(rssFeed));
        String result = controller.viewFeed("1", new ExtendedModelMap());

        Assert.assertTrue("Controller should return 'viewFeed'",
                !result.isEmpty() && result.equals("viewFeed"));

        Mockito.verify(repository).findById(1);
        Mockito.validateMockitoUsage();
    }
}
