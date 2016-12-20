package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        repository.delete(id,userId);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return repository.get(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>)repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getFilterAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        return repository.getBetween(startDate,endDate,startTime,endTime,userId);
    }



    @Override
    public void update(Meal meal, int userId) {
        repository.save(meal,userId);
    }
}
