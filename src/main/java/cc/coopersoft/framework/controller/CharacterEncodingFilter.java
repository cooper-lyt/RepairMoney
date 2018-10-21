package cc.coopersoft.framework.controller;

import org.apache.deltaspike.core.api.lifecycle.Destroyed;
import org.apache.deltaspike.core.api.lifecycle.Initialized;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.Enumerated;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Logger;

@WebFilter(filterName ="CharacterEncodingFilter", urlPatterns = {"/*"})
public class CharacterEncodingFilter implements Filter {

    @Inject
    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {



        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

    public void onCreate(@Observes @Initialized HttpServletRequest request) {
        try {
            logger.config(request.getCharacterEncoding() + "set to  utf-8 encoding on deltaspike");
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Starting to process request for: " + request.getRequestURI());
    }

    public void onDestroy(@Observes @Destroyed HttpServletRequest request) {
        System.out.println("Finished processing request for: " + request.getRequestURI());
    }

}
