package com.jurijz.rssfeed.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * Created by jurijz on 10/2/2018.
 */
@Entity
@Table(name = "feeds.feeds")
public class RssFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String url;

    private String title;

    @Column(name = "last_update")
    private Date lastUpdate;

    @NotEmpty
    @Column(name = "feed_name")
    private String feedName;

    @OneToMany(mappedBy = "rssFeed", targetEntity = RssItem.class, cascade = CascadeType.ALL)
    private List<RssItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public List<RssItem> getItems() {
        return items;
    }

    public void setItems(List<RssItem> items) {
        this.items = items;
    }
}
