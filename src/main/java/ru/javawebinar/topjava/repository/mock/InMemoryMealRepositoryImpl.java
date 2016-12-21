package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

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

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach((meal) -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        try {
            if (repository.get(meal.getId()).getUserId() != userId) return null;
        } catch (Exception ignored) {
        }

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
            repository.remove(id);
            return true;
        } else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        if (repository.containsKey(id) && repository.get(id).getUserId() == userId) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {

        return repository.entrySet().stream().filter(meal -> meal.getValue().getUserId() == userId).map(Map.Entry::getValue).sorted((a, b) -> (-1) * a.getDateTime().compareTo(b.getDateTime())).collect(Collectors.toList());

    }

    @Override
    public Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        List<Meal> listmeal = getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(Collectors.toList());
        return listmeal.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}
