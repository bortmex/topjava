package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Галина on 10.12.2016.
 */
public class MealDaoMemory implements MealDao{

    private ConcurrentHashMap<Integer, Meal> listMeals =  new ConcurrentHashMap<>();

    public static AtomicInteger index = new AtomicInteger(0);

    public MealDaoMemory() {
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        System.out.println("qewrew");
    }

    @Override
    public void add(Meal users) {
        index.getAndIncrement();
        users.setId(index.get());
        listMeals.put(index.get(), users);
    }

    @Override
    public void update(Meal meal) {
        listMeals.put(meal.getId(), meal);
    }

    @Override
    public void remove(Integer id) {
        listMeals.remove(Integer.parseInt(id.toString()));
    }

    @Override
    public Meal getById(Integer id) {
        return listMeals.get(Integer.parseInt(id.toString()));
    }

    @Override
    public Collection<Meal> list() {
        return listMeals.values();
    }

    @Override
    public List<Meal> queryFindByName(String name) {
        return null;
    }
}

