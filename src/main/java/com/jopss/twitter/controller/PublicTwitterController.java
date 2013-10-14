package com.jopss.twitter.controller;

import com.jopss.twitter.model.TwitterForm;
import com.jopss.twitter.util.TwitterUtils;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Classe controladora de tela para receber requisicoes sobre acessos publicos
 * a timeline de um usuario.
 */
@Controller
@RequestMapping("twitter/public")
public class PublicTwitterController {

    @Autowired
    private TwitterUtils twitterUtils;
    
    /**
     * Abre a tela inicialmente.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model, HttpSession session) throws TwitterException {
        model.addAttribute("twitterForm", new TwitterForm());
        return "public/show";
    }

    /**
     * Retorna a timeline de um usuario.
     */
    @RequestMapping(value = "/user/timeline/", method = RequestMethod.POST)
    public String getTimeline(TwitterForm twitterForm, Model model, HttpSession session) throws TwitterException {

        try {

            Twitter twitter = twitterUtils.getNewInstancePublic(session);
            twitterForm.setStatusList(twitter.getUserTimeline(twitterForm.getUsername()));
            model.addAttribute("msgSuccess", "twitter.getTimeline.success");

        } catch (TwitterException ex) {
            model.addAttribute("msgError", ex.getMessage());
        }

        model.addAttribute("twitterForm", twitterForm);
        return "public/show";
    }

}
