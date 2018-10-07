package com.jurijz.rssfeed.service;

import com.jurijz.rssfeed.domain.RssFeed;

import java.util.List;

/**
 * Created by jurijz on 10/6/2018.
 */
public interface RssFeedRepository {

    List<RssFeed> loadAll();

    RssFeed load(Integer id);

    void save(RssFeed rssFeed);
}
