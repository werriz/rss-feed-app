package com.jurijz.rssfeed.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jurijz on 10/2/2018.
 */
@Entity
@Table(name = "feeds.items")
public class RssItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String link;

    private String description;

    private Date published;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_id", nullable = false)
    private RssFeed rssFeed;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public RssFeed getRssFeed() {
        return rssFeed;
    }

    public void setRssFeed(RssFeed rssFeed) {
        this.rssFeed = rssFeed;
    }
}
