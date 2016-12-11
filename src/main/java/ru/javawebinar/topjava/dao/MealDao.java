package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Галина on 10.12.2016.
 */
public interface MealDao {
    public void add(Meal users);

    public void update(Meal users);

    public void remove(int id);

    public Meal getById(int id);

    public List<Meal> list();

    public List<Meal> queryFindByName(String name);
}
