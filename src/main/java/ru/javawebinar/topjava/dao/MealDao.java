package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Галина on 10.12.2016.
 */
public interface MealDao {
    public void add(Meal meal);

    public void update(Meal meal);

    public void remove(Integer id);

    public Meal getById(Integer id);

    public Collection<Meal> list();

    public List<Meal> queryFindByName(String name);
}
