package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Галина on 10.12.2016.
 */
public interface MealDao {
    public void add(Meal meal);

    public void update(Meal meal);

    public void remove(int id);

    public Meal getById(int id);

    public List<Meal> list();

    public List<Meal> queryFindByName(String name);
}
