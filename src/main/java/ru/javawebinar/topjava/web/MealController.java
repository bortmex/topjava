package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by alexa on 24.01.2017.
 */
@Controller
public class MealController extends MealRestController{

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        model.addAttribute("meal", new Meal());
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
        model.addAttribute("meals", getAll());
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
    public String addMeal(@ModelAttribute("meal") Meal meal){
        if(meal.getId() == null){
            meal.setDateTime( LocalDateTime.now());
            create(meal);
        }else {
            meal.setDateTime( LocalDateTime.now());
            update(meal,meal.getUser().getId());
        }

        return "redirect:/meals";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        AuthorizedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping("meal")
    public String userData(Model model){
        model.addAttribute("meal", new Meal());
        return "meal";
    }
}
