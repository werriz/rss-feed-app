package com.jurijz.rssfeed.service;

import com.jurijz.rssfeed.domain.RssFeed;
import org.hibernate.*;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jurijz on 10/6/2018.
 */
@Repository
public class RssFeedRepositoryImpl implements RssFeedRepository {

    private final HibernateTemplate hibernateTemplate;

    public RssFeedRepositoryImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Transactional
    @Override
    public List<RssFeed> loadAll() {
        return hibernateTemplate.loadAll(RssFeed.class);
    }

    @Transactional
    @Override
    public RssFeed load(Integer id) {
        RssFeed feed = hibernateTemplate.load(RssFeed.class, id);
        Hibernate.initialize(feed.getItems()); //Lazy initialization (Proxy design pattern)
        return feed;
    }

    @Transactional
    @Override
    public void save(RssFeed rssFeed) {
        hibernateTemplate.saveOrUpdate(rssFeed);
    }
}
