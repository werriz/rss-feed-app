package com.jurijz.rssfeed.service;

import com.jurijz.rssfeed.domain.RssFeed;
import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.util.List;

/**
 * Created by jurijz on 10/2/2018.
 */
public interface RssFeedService {

    List<RssFeed> loadRssFeeds();

    RssFeed loadRssFeeds(Integer id);

    void save(RssFeed rssFeed) throws IOException, FeedException;
}
