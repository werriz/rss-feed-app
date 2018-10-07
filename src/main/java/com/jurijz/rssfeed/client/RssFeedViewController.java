package com.jurijz.rssfeed.client;

import com.jurijz.rssfeed.service.RssFeedService;
import com.jurijz.rssfeed.domain.RssFeed;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

/**
 * Created by jurijz on 10/2/2018.
 */
@Controller
public class RssFeedViewController {

    //Strategy Design pattern usage
    private final RssFeedService rssFeedService;

    //Singleton Design pattern usage
    @Autowired
    public RssFeedViewController(RssFeedService rssFeedService) {
        this.rssFeedService = rssFeedService;
    }

    @RequestMapping("/")
    public String viewFeeds(Model model) {
        List<RssFeed> feeds = rssFeedService.loadRssFeeds();
        model.addAttribute("feeds", feeds);
        return "viewFeeds";
    }

    @RequestMapping("/viewFeed")
    public String viewFeed(@PathParam("id") String id, Model model) {
        if (id != null) {
            model.addAttribute("feed", rssFeedService.loadRssFeeds(Integer.valueOf(id)));
        }
        return "viewFeed";
    }

    @RequestMapping("/addFeed")
    public String addFeed(Model model) {
        model.addAttribute("rssFeed", new RssFeed());
        return "addFeed";
    }

    @PostMapping("/addFeed")
    public String rssFeedSubmit(@ModelAttribute @Valid RssFeed rssFeed,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addFeed";
        } else {
            try {
                rssFeedService.save(rssFeed);
            } catch (IOException | FeedException ioe) {
                bindingResult.rejectValue("url", "error.user",
                        "Providen URL is not valid.");
                return "addFeed";
            }
            return "redirect:/";
        }
    }
}
