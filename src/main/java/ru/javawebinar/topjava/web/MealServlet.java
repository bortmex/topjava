package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by alexa on 08.12.2016.
 */
public class MealServlet  extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao mealDao = new MealDaoMemory();
    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String LIST_MEAL = "/listUser.jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        String forward="";
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.remove(id);
            forward = LIST_MEAL;
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.list(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if ("insert".equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            Meal meal = new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 0);
            request.setAttribute("meal", meal);
        } else if ("edit".equalsIgnoreCase(action)) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.getById(id);
            request.setAttribute("meal", meal);
        }


        request.setAttribute("list", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
        //response.sendRedirect("meals.jsp");
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal = null;

        String forward="";
        String action = req.getParameter("action");

        /*if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            mealDao.remove(mealId);
        } else if (action.equalsIgnoreCase("edit")){
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal user = mealDao.getById(mealId);
        }  else if(action.equalsIgnoreCase(req.getParameter("frmAddMeal"))) {*/

            req.setCharacterEncoding("UTF-8");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                    .ofPattern("HH:mm:ss MM/dd/uuuu", Locale.US)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime date = LocalDateTime.parse(req.getParameter("dob"), dateTimeFormatter);
            meal = new Meal(LocalDateTime.of(date.toLocalDate(), date.toLocalTime()), req.getParameter("mealdescription"), Integer.parseInt(req.getParameter("calories")));
            mealDao.add(meal);

        req.setAttribute("list", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        req.getRequestDispatcher("meals.jsp").forward(req,resp);
    }
}
