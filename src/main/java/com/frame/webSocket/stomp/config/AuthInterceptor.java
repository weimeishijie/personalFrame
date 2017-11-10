package com.frame.webSocket.stomp.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by mj on 2017/11/7.
 */
@Component
public class AuthInterceptor implements Filter {

    FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String URL = httpServletRequest.getRequestURL().toString();
        System.out.println("URL: "+URL);
    }

    public void destroy() {
        filterConfig = null;
    }
}
