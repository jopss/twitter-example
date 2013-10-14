package com.jopss.twitter.controller;

import com.jopss.twitter.model.TwitterCredential;
import com.jopss.twitter.model.TwitterForm;
import com.jopss.twitter.util.TwitterUtils;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Classe controladora de tela para receber requisicoes sobre autorizacao de acesso
 * a conta de um usuario no Twitter.
 * 
 * Apos o acesso autorizado, pode-se acessar dados, timeline e efetuar twits.
 */
@Controller
@RequestMapping("twitter/auth")
public class AuthTwitterController {

    @Autowired
    private TwitterUtils twitterUtils;
    
    /**
     * Abre a tela inicialmente.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String show(Model model, HttpSession session) throws TwitterException {

        TwitterForm twitterForm = new TwitterForm();
        twitterForm.setCredentials(TwitterCredential.findAll());
        this.addOAuthUrlRequestToken(model, twitterForm, session);
        return "auth/show";
    }

    /**
     * Retorna a timeline de um usuario jah autorizado.
     */
    @RequestMapping(value = "/timeline/user/{userId}/", method = RequestMethod.GET)
    public String getTimeline(@PathVariable Long userId, Model model, HttpSession session) throws TwitterException {

        TwitterForm twitterForm = new TwitterForm();

        try {

            TwitterCredential cred = TwitterCredential.findUserCredential(userId);

            Twitter twitter = twitterUtils.getSameInstance(session);
            AccessToken accessToken = new AccessToken(cred.getToken(), cred.getSecret(), cred.getId());
            twitter.setOAuthAccessToken(accessToken);

            twitterForm.setStatusList(twitter.getHomeTimeline());
            model.addAttribute("msgSuccess", "twitter.getTimeline.success");

        } catch (TwitterException ex) {
            model.addAttribute("msgError", ex.getMessage());
        }

        twitterForm.setCredentials(TwitterCredential.findAll());
        this.addOAuthUrlRequestToken(model, twitterForm, session);
        return "auth/show";
    }

    /**
     * Retorna a timeline de um novo usuario, ou seja, inserido o PIN de autorizacao da conta deste usuario.
     */
    @RequestMapping(value = "/timeline/new/", method = RequestMethod.POST)
    public String getNewTimeline(TwitterForm twitterForm, Model model, HttpSession session) throws TwitterException {

        try {

            Twitter twitter = twitterUtils.getSameInstance(session);
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
        this.addOAuthUrlRequestToken(model, twitterForm, session);
        return "auth/show";
    }

    /**
     * Refaz a URL de autorizacao para o link da tela.
     */
    private void addOAuthUrlRequestToken(Model model, TwitterForm twitterForm, HttpSession session) throws TwitterException {

        Twitter twitter = twitterUtils.getNewInstanceAuth(session);
        
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken();
            twitterForm.setUrlRequestToken(requestToken.getAuthorizationURL());
        } catch (IllegalStateException ex) {
            if (ex.getMessage().contains("Access token already available")) {
                model.addAttribute("msgValidate", "twitter.access.validate");
            } else {
                model.addAttribute("msgError", ex.getMessage());
            }
        }

        model.addAttribute("twitterForm", twitterForm);
    }
}
