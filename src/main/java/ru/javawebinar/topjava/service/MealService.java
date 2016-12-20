package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {

    Meal save(Meal meal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    List<MealWithExceed> getAll(int userId);

    Collection<MealWithExceed> getFilterAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId );

    void update(Meal meal, int userId);
}
