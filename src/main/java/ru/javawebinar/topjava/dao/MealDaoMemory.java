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
    public MealDaoMemory() {
        listMeals.put(listMeals.size()+1,new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        listMeals.put(listMeals.size()+1,new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        listMeals.put(listMeals.size()+1,new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        listMeals.put(listMeals.size()+1,new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        listMeals.put(listMeals.size()+1,new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        listMeals.put(listMeals.size()+1,new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private int getArrayId(){
        Iterator<Map.Entry<Integer,Meal>> iter = listMeals.entrySet().iterator();
        Map.Entry<Integer,Meal> entry = null;
        while(iter.hasNext()) {
            entry = iter.next();
        }
        return entry.getValue().getId()+1;
    }

    @Override
    public void add(Meal users) {
        listMeals.put(getArrayId(), users);
    }

    @Override
    public void update(Meal meal) {
        int id= meal.getId();
        remove(meal.getId());
        listMeals.put(id, meal);
    }

    @Override
    public void remove(int id) {
       listMeals.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return listMeals.get(id);
    }

    @Override
    public List<Meal> list() {
        List<Meal> list = new ArrayList<>();
        for (Map.Entry<Integer,Meal> meal:listMeals.entrySet()) {
            meal.getValue().setId(meal.getKey());
            list.add(meal.getValue());
        }
        return list;
    }

    @Override
    public List<Meal> queryFindByName(String name) {
        return null;
    }
}

