package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by alexa on 08.12.2016.
 */
public class MealServlet  extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        List<MealWithExceed> listMeals =  Arrays.asList(
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500,true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, false),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,true),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, false),
                new MealWithExceed(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,false)
        );
        request.setAttribute("list", listMeals);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
        //response.sendRedirect("meals.jsp");
    }
}
