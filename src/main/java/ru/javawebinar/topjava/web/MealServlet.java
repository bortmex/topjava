package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
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
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by alexa on 08.12.2016.
 */
public class MealServlet  extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao mealDao;
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "mealEdit.jsp";
    private static String LIST_MEAL = "listsMeals.jsp";

    public MealServlet() {
        super();
        this.mealDao = new MealDaoMemory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        String forward="";
        String action = request.getParameter("action");
        if (action != null && !action.isEmpty()){
            if (action.equalsIgnoreCase("delete")){
            int userId = Integer.parseInt(request.getParameter("mealId"));
            mealDao.remove(userId);
            forward = LIST_MEAL;
                //response.sendRedirect("meal");
            request.setAttribute("list", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX,2000));
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int userId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDao.getById(userId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = LIST_MEAL;
            request.setAttribute("list", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX,2000));
        } else {
            forward = INSERT_OR_EDIT;
        }}
        else {
            forward = LIST_MEAL;
        }
        /*RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);*/
        request.setAttribute("list", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX,2000));
        request.getRequestDispatcher(forward).forward(request, response);
        //response.sendRedirect("listsMeals.jsp");
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal;
        req.setCharacterEncoding("UTF-8");
        LocalDateTime date = LocalDateTime.parse(req.getParameter("dob"));
        meal = new Meal(LocalDateTime.of(date.toLocalDate(), date.toLocalTime()), req.getParameter("mealdescription"), Integer.parseInt(req.getParameter("calories")));

        String mealId = req.getParameter("mealId");
        if(mealId == null || mealId.isEmpty())
        {
            meal.setId(mealDao.getArrayId());
            mealDao.add(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(mealId));
            mealDao.update(meal);
        }
        RequestDispatcher view = req.getRequestDispatcher(LIST_MEAL);
        req.setAttribute("list", MealsUtil.getFilteredWithExceededByCycle(mealDao.list(), LocalTime.MIN, LocalTime.MAX,2000));
        view.forward(req, resp);
    }
}
