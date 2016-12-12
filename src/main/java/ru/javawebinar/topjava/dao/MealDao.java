package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Галина on 10.12.2016.
 */
public interface MealDao {
    public void add(Meal meal);

    public void update(Meal meal);

    public void remove(int id);

    public Meal getById(int id);

    public CopyOnWriteArrayList<Meal> list();

    public CopyOnWriteArrayList<Meal> queryFindByName(String name);
}
