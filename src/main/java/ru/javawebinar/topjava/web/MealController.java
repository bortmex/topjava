package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Created by alexa on 24.01.2017.
 */
@Controller
public class MealController extends AbstractMealRestController{

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @RequestMapping("/delete/{id}")
    public String deleteMeal(@PathVariable("id") int id){
        delete(id);
        return "redirect:/meals";
    }

    @RequestMapping("/edit/{id}")
    public String editMeal(@PathVariable("id") int id, Model model){
        model.addAttribute("meal", get(id));
        return "meal";
    }

    @RequestMapping(value = "meals/filter", method = RequestMethod.POST)
    public String filterMeal(HttpServletRequest request){
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals", getBetween(startDate,startTime,endDate,endTime));
        return "meals";
    }

    @RequestMapping(value = "meal/add", method = RequestMethod.POST)
    public String addMeal(HttpServletRequest request){
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if(request.getParameter("id").isEmpty()){
            create(meal);
        }else {
            update(meal,getId(request));
        }
        request.setAttribute("meals", getAll());

        return "meals";
    }

    @RequestMapping("meal")
    public String userData(Model model){
        model.addAttribute("meal", new Meal());
        return "meal";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
