package com.jurijz.rssfeed;

import com.jurijz.rssfeed.client.RssFeedViewController;
import com.jurijz.rssfeed.domain.RssFeed;
import com.jurijz.rssfeed.service.RssFeedRepository;
import com.jurijz.rssfeed.service.RssFeedRepositoryImpl;
import com.jurijz.rssfeed.service.RssFeedService;
import com.jurijz.rssfeed.service.RssFeedServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.never;

/**
 * Created by jurijz on 10/6/2018.
 */
public class RssFeedTest {

    private RssFeedViewController controller;
    private HibernateTemplate hibernateTemplate;
    private RssFeed rssFeed;
    private BindingResult bindingResult;

    private static final String TEST_URL = "https://www.15min.lt/rss";

    @Before
    public void setUp() {
        rssFeed = new RssFeed();
        bindingResult = new MapBindingResult(new HashMap<>(), "rssFeed");

        hibernateTemplate = Mockito.mock(HibernateTemplate.class);
        RssFeedRepository repository = new RssFeedRepositoryImpl(hibernateTemplate);
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
        Assert.assertTrue("RssFeed title should be filled",
                !rssFeed.getTitle().isEmpty());

        Mockito.verify(hibernateTemplate).saveOrUpdate(rssFeed);
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

        Mockito.verify(hibernateTemplate, never()).saveOrUpdate(rssFeed);
    }

    @Test
    public void viewRssFeeds() {
        Mockito.when(hibernateTemplate.loadAll(RssFeed.class))
                .thenReturn(Collections.singletonList(rssFeed));
        String result = controller.viewFeeds(new ExtendedModelMap());

        Assert.assertTrue("Controller should return 'viewFeeds'",
                !result.isEmpty() && result.equals("viewFeeds"));


        Mockito.verify(hibernateTemplate).loadAll(RssFeed.class);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void viewSingleRssFeed() {
        rssFeed.setId(1);
        Mockito.when(hibernateTemplate.load(RssFeed.class, 1))
                .thenReturn(rssFeed);
        String result = controller.viewFeed("1", new ExtendedModelMap());

        Assert.assertTrue("Controller should return 'viewFeed'",
                !result.isEmpty() && result.equals("viewFeed"));

        Mockito.verify(hibernateTemplate).load(RssFeed.class,1);
        Mockito.validateMockitoUsage();
    }
}
