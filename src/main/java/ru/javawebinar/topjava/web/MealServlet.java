package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRestController repository;
    private ClassPathXmlApplicationContext springContext;
    private InMemoryUserRepositoryImpl inMemoryUserRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MealRestController(new MealServiceImpl(new InMemoryMealRepositoryImpl()));
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        inMemoryUserRepository = springContext.getBean(InMemoryUserRepositoryImpl.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if(action==null){
            String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if(meal.getId()==null)
        repository.create(meal); else {
            repository.update(meal);
            }
        response.sendRedirect("meals");} else if(action.equals("filter")){
            LocalDate startDate = DateTimeUtil.parseLocalDate(resetParam("fromDate",request));
            LocalDate endDate = DateTimeUtil.parseLocalDate(resetParam("toDate",request));

            LocalTime startTime = DateTimeUtil.parseLocalTime(resetParam("fromTime",request));
            LocalTime endTime = DateTimeUtil.parseLocalTime(resetParam("toTime",request));

            if(startDate==null) startDate=LocalDate.MIN; if(endDate==null) endDate=LocalDate.MAX;
            if(startTime==null) startTime=LocalTime.MIN; if(endTime==null) endTime=LocalTime.MAX;

            LOG.info("startDate == " + startDate + "\tendDate == " + endDate + "\t startTime == " + startTime + "\t endTime == " + endTime);

            request.setAttribute("userId",AuthorizedUser.id());
            request.setAttribute("userName",inMemoryUserRepository.get(AuthorizedUser.id()).getName() );
            request.setAttribute("meals",  repository.getBetween(startDate, endDate, startTime, endTime, AuthorizedUser.id()));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

    }

    private String resetParam(String param, HttpServletRequest request){
        String value = request.getParameter(param);
        request.setAttribute(value, request);
        return value;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("userId",AuthorizedUser.id());
            request.setAttribute("userName", springContext.getBean(InMemoryUserRepositoryImpl.class).get(AuthorizedUser.id()).getName());
            request.setAttribute("meals",
                    repository.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            repository.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    repository.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}

