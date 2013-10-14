package com.jopss.twitter.controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.TwitterException;

@Controller
@RequestMapping("twitter")
public class AppTwitterController {
    
    /**
     * Abre a tela inicialmente.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session) throws TwitterException {
        return "home";
    }

}
