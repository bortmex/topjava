package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Галина on 10.12.2016.
 */
public class MealDaoMemory implements MealDao{

    private CopyOnWriteArrayList<Meal> listMeals =  new CopyOnWriteArrayList<>();
    public MealDaoMemory() {
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        setIdDefaltGeneration();
    }

    private void setIdDefaltGeneration(){
        for (int i = 0; i < list().size(); i++) {
            Meal meal = list().get(i);
            meal.setId(i+1);
        }
    }

    private int getArrayId(){
        int[] arrId = new int[listMeals.size()];
        for (int i = 0; i < listMeals.size(); i++) {
            arrId[i] = listMeals.get(i).getId();
        }
        Arrays.sort(arrId);
        int number = arrId.length;

        int j = 1;
        for (int i = 0; i < arrId.length; i++) {
            if(arrId[i]!=j) {number = j; break;}
            j++;
        }

        return number;
    }

    @Override
    public void add(Meal users) {
        users.setId(getArrayId());
        listMeals.add(users);
    }

    @Override
    public void update(Meal meal) {
        int id= meal.getId();
        remove(meal.getId());
        meal.setId(id);
        listMeals.add(meal);
    }

    @Override
    public void remove(int id) {

        int i =0;
        for (Meal meal:listMeals) {
            if(meal.getId()==id) {listMeals.remove(i);break;}
                i++;
        }
    }

    @Override
    public Meal getById(int id) {
        int i =0;
        for (Meal meal:listMeals) {
            if(meal.getId()==id) return listMeals.get(i);
            i++;
        }
        return null;
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

