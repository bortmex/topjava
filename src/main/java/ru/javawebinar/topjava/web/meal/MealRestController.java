package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll() {
        LOG.info("getAll");
        return service.getAll(AuthorizedUser.id());
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id,AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return service.save(meal,AuthorizedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id,AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        meal.setId(id);
        LOG.info("update " + meal);
        service.update(meal,AuthorizedUser.id());
    }

    public Collection<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int id){
        return service.getFilterAll(startDate,endDate,startTime,endTime,id);
    }
}
