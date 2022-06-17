package org.example.common.servlet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter 拦截
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/17 11:31 AM
 */
public class CachedBodyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            request = new CachedBodyHttpServletRequest((HttpServletRequest) request);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}