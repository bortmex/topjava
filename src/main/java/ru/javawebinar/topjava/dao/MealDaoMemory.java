package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Галина on 10.12.2016.
 */
public class MealDaoMemory implements MealDao{

    private List<Meal> listMeals =  new ArrayList<>();
    public MealDaoMemory() {
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        setIdDefaltGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        setIdDefaltGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        setIdDefaltGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        setIdDefaltGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        setIdDefaltGeneration();
        listMeals.add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        setIdDefaltGeneration();

    }

    private void setIdDefaltGeneration(){
        Meal meal = list().get(listMeals.size()-1);
        meal.setId(new AtomicInteger(listMeals.size()));
    }

    private int getArrayId(){
        int[] arrId = new int[listMeals.size()];
        for (int i = 0; i < listMeals.size(); i++) {
            arrId[i] = Integer.parseInt(listMeals.get(i).getId().toString());
        }
        Arrays.sort(arrId);
        int number = arrId.length;

        int j = 1;
        System.out.println(Arrays.toString(arrId));
        for (int i = 0; i < arrId.length; i++) {
            if(arrId[i]!=j) {number = j; break;}
            j++;
        }

        return number;
    }

    @Override
    public void add(Meal users) {
        users.setId(new AtomicInteger(getArrayId()));
        listMeals.add(users);
    }

    @Override
    public void update(Meal meal) {
        AtomicInteger id= meal.getId();
        remove(Integer.parseInt(meal.getId().toString()));
        meal.setId(id);
        listMeals.add(meal);
    }

    @Override
    public void remove(int id) {

        int i =0;
        for (Meal meal:listMeals) {
            if(meal.getId().floatValue()==new AtomicInteger(id).floatValue()) {listMeals.remove(i);break;}
                i++;
        }
    }

    @Override
    public Meal getById(int id) {
        int i =0;
        for (Meal meal:listMeals) {
            if(meal.getId().floatValue()==new AtomicInteger(id).floatValue()) return listMeals.get(i);
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

