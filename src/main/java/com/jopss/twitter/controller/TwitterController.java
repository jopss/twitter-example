package com.jopss.twitter.controller;

import com.jopss.twitter.model.TwitterForm;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

@Controller
@RequestMapping("twitter")
public class TwitterController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) throws TwitterException {

        TwitterForm twitterForm = new TwitterForm();
        twitterForm.setUrlRequestToken( TwitterFactory.getSingleton().getOAuthRequestToken().getAuthorizationURL() );
        
        model.addAttribute("twitterForm", twitterForm);
        return "show";
    }

    @RequestMapping(value = "/timeline", method = RequestMethod.POST)
    public String getTimeline(TwitterForm twitterForm, Model model) {

        try {

            Twitter twitter = TwitterFactory.getSingleton();
            AccessToken accessToken = twitter.getOAuthAccessToken(twitterForm.getPin());
            //twitter.setOAuthAccessToken(accessToken); //para quando estiver jah gravado
            Long id = twitter.verifyCredentials().getId();
            String token = accessToken.getToken();
            String secret = accessToken.getTokenSecret();

            List<Status> statusList = twitter.getHomeTimeline();

            model.addAttribute("timeline", statusList);
            model.addAttribute("msgSuccess", "twitter.getTimeline.success");

        } catch (TwitterException ex) {
            model.addAttribute("msgError", ex.getMessage());
        }

        model.addAttribute("twitterForm", twitterForm);
        return "show";
    }
}
