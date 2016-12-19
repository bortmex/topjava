package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.*;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Created by alexa on 20.12.2016.
 */
public class FilterConnect  implements Filter {

    private FilterConfig config = null;
    private boolean active = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        String act = config.getInitParameter("active");
        if (act != null)
            active = (act.toUpperCase().equals("TRUE"));
    }

    @Override
    public void doFilter (ServletRequest request, ServletResponse response,
                          FilterChain chain) throws IOException,
            ServletException
    {
        if (active)
        {
            String date = request.getParameter("date");

        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        config = null;
    }
}
