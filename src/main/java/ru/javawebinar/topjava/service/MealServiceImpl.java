package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;
/**
 * GKislin
 * 06.03.2015.
 */
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int id) {
        return repository.getAll();
    }

    @Override
    public void update(Meal meal) {
        repository.save(meal);
    }
}
