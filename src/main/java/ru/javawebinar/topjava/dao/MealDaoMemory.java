package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Галина on 10.12.2016.
 */
public class MealDaoMemory implements MealDao{

    private List<Meal> listMeals =  new ArrayList<>();
    public MealDaoMemory() {
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        setIdGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        setIdGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        setIdGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        setIdGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        setIdGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        setIdGeneration();

    }

    private void setIdGeneration(){
        Meal meal = list().get(listMeals.size()-1);
        meal.setId(new AtomicInteger(listMeals.size()));
    }

    @Override
    public void add(Meal users) {
        listMeals.add(users);
    }

    @Override
    public void update(Meal users) {

    }

    @Override
    public void remove(int id) {
        listMeals.remove(id-1);
    }

    @Override
    public Meal getById(int id) {
        return listMeals.get(id-1);
    }

    @Override
    public List<Meal> list() {
        return listMeals;
    }

    @Override
    public List<Meal> queryFindByName(String name) {
        return null;
    }
}

