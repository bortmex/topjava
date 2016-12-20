package ru.javawebinar.topjava.repository.mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((meal) -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        LOG.info("save " + meal + "; userId " + userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.containsKey(id) && repository.get(id).getUserId() == userId) {
            LOG.info("delete " + id + "; userId " + userId);
            repository.remove(id);
            return true;
        } else throw new NotFoundException("Не найдено");
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.containsKey(id) && repository.get(id).getUserId() == userId) {
            LOG.info("get " + id + "; userId " + userId);
            return repository.get(id);
        } else throw new NotFoundException("Не найдено");
    }

    @Override
    public Collection<MealWithExceed> getAll(int userId) {
        LOG.info("getAll");
        List<Meal> listMeal = repository.values().stream().filter(meal -> meal.getUserId() == userId).sorted((a, b) -> (-1) * a.getDateTime().compareTo(b.getDateTime())).collect(Collectors.toList());
        if (listMeal.size() == 0) return null;
        else return MealsUtil.getWithExceeded(listMeal , MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public Collection<MealWithExceed> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        List<MealWithExceed> listmeal = getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDateTime().toLocalDate(), startDate, endDate))
                .collect(Collectors.toList());
        return listmeal.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
