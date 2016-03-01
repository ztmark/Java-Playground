package com.mark.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Author: Mark
 * Date  : 16/3/2.
 */
public class TestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("------------servlet filter---------------");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
