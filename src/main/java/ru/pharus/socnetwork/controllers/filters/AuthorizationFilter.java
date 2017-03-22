package ru.pharus.socnetwork.controllers.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pharus.socnetwork.controllers.LoginController;
import ru.pharus.socnetwork.service.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authorization common servlet filter
 */

@WebFilter("/user")
public class AuthorizationFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.debug("Enter to AuthorizationFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getServletPath();
        //boolean allowedRequest = false;

        if(url!=null) {
            log.debug("user request url mapped to " + url);
            //allowedRequest = true;
        }

        HttpSession session = httpRequest.getSession(true);
        if (session.getAttribute("user_id") == null) {
            log.info(String.format("Session %s not authorized, redirect to /", session.getId()));
            httpResponse.sendRedirect("/");
            return;
        }

        chain.doFilter(request,response);
        log.debug("Quit from AuthorizationFilter");
    }
}
