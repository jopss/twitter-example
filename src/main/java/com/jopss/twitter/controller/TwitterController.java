package com.jopss.twitter.controller;

import com.jopss.twitter.model.TwitterCredential;
import com.jopss.twitter.model.TwitterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@Controller
@RequestMapping("twitter")
public class TwitterController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model) throws TwitterException {

        TwitterForm twitterForm = new TwitterForm();
        twitterForm.setCredentials(TwitterCredential.findAll());
        this.addOAuthUrlRequestToken(model, twitterForm);
        return "show";
    }

    @RequestMapping(value = "/timeline/user/{userId}/", method = RequestMethod.GET)
    public String getTimeline(@PathVariable Long userId, Model model) throws TwitterException {

        TwitterForm twitterForm = new TwitterForm();

        try {

            Twitter twitter = TwitterFactory.getSingleton();
            TwitterCredential cred = TwitterCredential.findUserCredential(userId);

            AccessToken accessToken = new AccessToken(cred.getToken(), cred.getSecret(), cred.getId());
            twitter.setOAuthAccessToken(accessToken);

            twitterForm.setStatusList(twitter.getHomeTimeline());
            model.addAttribute("msgSuccess", "twitter.getTimeline.success");

        } catch (TwitterException ex) {
            model.addAttribute("msgError", ex.getMessage());
        }

        twitterForm.setCredentials(TwitterCredential.findAll());
        this.addOAuthUrlRequestToken(model, twitterForm);
        return "show";
    }

    @RequestMapping(value = "/timeline/new/", method = RequestMethod.POST)
    public String getNewTimeline(TwitterForm twitterForm, Model model) throws TwitterException {

        try {

            Twitter twitter = TwitterFactory.getSingleton();
            AccessToken accessToken = twitter.getOAuthAccessToken(twitterForm.getPin());

            TwitterCredential.addCredencial(twitter.verifyCredentials().getId(), twitter.verifyCredentials().getName(), accessToken.getToken(), accessToken.getTokenSecret());

            twitterForm.setStatusList(twitter.getHomeTimeline());
            twitterForm.setPin(null);
            twitterForm.setUrlRequestToken(null);
            model.addAttribute("msgSuccess", "twitter.getTimeline.success.new");

        } catch (TwitterException ex) {
            model.addAttribute("msgError", ex.getMessage());
        }

        twitterForm.setCredentials(TwitterCredential.findAll());
        this.addOAuthUrlRequestToken(model, twitterForm);
        return "show";
    }

    private void addOAuthUrlRequestToken(Model model, TwitterForm twitterForm) throws TwitterException {

        Twitter twitter = TwitterFactory.getSingleton();
        
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            twitterForm.setUrlRequestToken(requestToken.getAuthorizationURL());
        } catch (IllegalStateException ex) {
            if (ex.getMessage().contains("Access token already available")) {
                model.addAttribute("msgValidate", "twitter.access.validate");
            } else {
                ex.printStackTrace();
                model.addAttribute("msgError", ex.getMessage());
            }
        }

        model.addAttribute("twitterForm", twitterForm);
    }
}
