package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {

    Meal save(Meal meal, int userId);


    void delete(int id, int userId);


    Meal get(int id, int userId);


    Collection<Meal> getAll(int userId);

    Collection<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId);

    Collection<Meal> getBetween(LocalTime startTime, LocalTime endTime, int userId);
}