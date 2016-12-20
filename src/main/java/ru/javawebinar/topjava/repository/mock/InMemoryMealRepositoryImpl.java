package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
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
        MealsUtil.MEALS.forEach((meal) -> save(meal,1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        meal.setUserId(userId);
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        Meal meal = repository.get(id);
        if(meal.getUserId()==userId) repository.remove(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if(meal.getUserId()==userId) return meal;
        else return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {

        List<Meal> listMeal = repository.entrySet().stream().filter(meal -> meal.getValue().getUserId() == userId).map(Map.Entry::getValue).collect(Collectors.toList());

        Collections.sort(listMeal, (a, b) -> (-1) * a.getDateTime().compareTo(b.getDateTime()));
        if (listMeal.size() == 0) return null;
        else return listMeal;
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

    public static void main(String[] args) {
        LocalDateTime ps = LocalDateTime.of(2015, Month.MAY, 30, 20, 0);

        System.out.println(ps.toLocalDate());
        System.out.println(ps.toLocalTime());
    }
}
