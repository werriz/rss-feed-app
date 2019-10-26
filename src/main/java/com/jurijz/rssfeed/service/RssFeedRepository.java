package com.jurijz.rssfeed.service;

import com.jurijz.rssfeed.domain.RssFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RssFeedRepository extends JpaRepository<RssFeed, Integer> {

}
