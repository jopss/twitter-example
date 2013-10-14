package com.jopss.twitter.util;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Classe necessaria para minimizar problemas com a API Twitter4J.
 * Na documentacao diz para criar um arquivo properties com as autorizacoes da aplicacao cadastrada
 * no Twitter. Mas dessa forma, ao tentar segundo acesso para novo usuario, ocorre erros de autorizacao.
 * 
 * Para resolver, eh necessario setar manualmente as chaves da aplicacao, para cada nova instancia.
 * Dessa forma novos acessos geram novas URL de requisicao, nao ocorrendo o problema.
 * 
 * Assim, o arquivo 'twitter4j.properties' descrito na documentacao desta versao da API nao eh necessario.
 */
@Component
public class TwitterUtils {

    @Autowired
    private ServletContextAwareImpl servletContextAwareImpl;
    
    /**
     * Retorna uma nova instancia de Twitter, limpando acessos anteriores. Nenhuma tentativa de chamada
     * com PIN sera aceito, pois a requisicao que gerou o PIN nao existe mais.
     */
    public Twitter getNewInstanceAuth(HttpSession session) {
        TwitterFactory tf = new TwitterFactory( getConfiguration().build());
        Twitter twitter = tf.getInstance();
        
        session.setAttribute("twitter-instance", twitter);
        
        return twitter;
    }
    
    /**
     * Retorna uma nova instancia de Twitter, limpando acessos anteriores. Configura para permitir
     * acessos publicos aos perfis (com restricoes), nao sendo necessario perdir confirmacao para
     * exibir a timeline publica.
     */
    public Twitter getNewInstancePublic(HttpSession session) throws TwitterException {
        
        ConfigurationBuilder cb = getConfiguration();
        cb.setDebugEnabled(true);
        cb.setApplicationOnlyAuthEnabled(true);
        cb.setUseSSL(true);
        
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        twitter.getOAuth2Token();
        
        session.setAttribute("twitter-instance", twitter);
        
        return twitter;
    }
    
    /**
     * Retorna a mesma instancia de Twitter gerada na 'request' anterior. Util para realizar operacoes
     * em sequencia, como por exemplo, requisitar autorizacao para um usuario e depois validar o PIN gerado.
     */
    public Twitter getSameInstance(HttpSession session){
        return (Twitter) session.getAttribute("twitter-instance");
    }
    
    private ConfigurationBuilder getConfiguration(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(servletContextAwareImpl.getServletCtx().getInitParameter("oauth.consumerKey"));
        cb.setOAuthConsumerSecret(servletContextAwareImpl.getServletCtx().getInitParameter("oauth.consumerSecret"));

        return cb;
    }
    
}
