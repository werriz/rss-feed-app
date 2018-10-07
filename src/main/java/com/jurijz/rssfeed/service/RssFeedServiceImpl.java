package com.jurijz.rssfeed.service;

import com.jurijz.rssfeed.domain.RssFeed;
import com.jurijz.rssfeed.domain.RssItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jurijz on 10/2/2018.
 */
@Service
public class RssFeedServiceImpl implements RssFeedService {

    private final RssFeedRepository rssFeedRepository;

    @Autowired
    public RssFeedServiceImpl(RssFeedRepository rssFeedRepository) {
        this.rssFeedRepository = rssFeedRepository;
    }

    @Transactional
    @Override
    public List<RssFeed> loadRssFeeds() {
        return rssFeedRepository.loadAll();
    }

    @Override
    public RssFeed loadRssFeeds(Integer id) {
        return rssFeedRepository.load(id);
    }

    @Transactional
    @Override
    public void save(RssFeed rssFeed) throws IOException, FeedException {
        updateRssFeed(rssFeed);

        rssFeedRepository.save(rssFeed);
    }

    /**
     * This method uses Rome Tools to download and parse xml from providen url
     * @param rssFeed
     * @throws IOException
     * @throws FeedException
     */
    private void updateRssFeed(RssFeed rssFeed) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new URL(rssFeed.getUrl())));

        rssFeed.setTitle(feed.getTitle());
        rssFeed.setLastUpdate(new Date());
        rssFeed.setItems(feed.getEntries().stream()
                .map(entry -> transformToItem(entry, rssFeed)).collect(Collectors.toList()));
    }

    /**
     * This method transforms RomeTools entry object to RssItem object
     * @param entry
     * @param rssFeed
     * @return RssItem
     */
    private RssItem transformToItem(final SyndEntry entry, final RssFeed rssFeed) {
        RssItem item = new RssItem();
        item.setTitle(entry.getTitle());
        item.setDescription(entry.getDescription().getValue());
        item.setLink(entry.getLink());
        item.setPublished(entry.getPublishedDate());
        item.setRssFeed(rssFeed);
        return item;
    }
}
