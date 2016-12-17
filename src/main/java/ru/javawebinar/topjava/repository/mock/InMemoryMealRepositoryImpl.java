package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepositoryImpl() {}

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if(meal.getUserId()==AuthorizedUser.id()){
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;}
        else return null;
    }

    @Override
    public void delete(int id) {
        if(id==AuthorizedUser.id()){
        repository.remove(id);}
    }

    @Override
    public Meal get(int id) {
        if(id==AuthorizedUser.id()){
        return repository.get(id);}
        else return null;
    }

    @Override
    public List<Meal> getAll() {

        List<Meal> listMeal = new ArrayList<>();
        for (Map.Entry<Integer, Meal> meal : repository.entrySet()) {
            if(meal.getValue().getUserId()==AuthorizedUser.id())
            listMeal.add(meal.getValue());
        }

        Collections.sort(listMeal, new Comparator<Meal>() {
            @Override
            public int compare(Meal a, Meal b) {
                return (-1) * a.getDateTime().compareTo(b.getDateTime());
            }
        });
        if (listMeal.size() == 0) return null;
        else return listMeal;
    }
}

