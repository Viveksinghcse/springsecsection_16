package com.viveksinghspringsecurity.filter;

import jakarta.servlet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class AuthoritiesloggingAtFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AuthoritiesloggingAtFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Authentication is in progress");
        chain.doFilter(request,response);
    }
}
