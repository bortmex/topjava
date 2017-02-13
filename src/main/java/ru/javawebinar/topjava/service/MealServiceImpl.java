package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.MealUtil;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public Collection<Meal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    @Cacheable("meals")
    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public Meal update(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        return checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Transactional
    @Override
    public Meal update(MealTo mealTo, int userId) throws NotFoundException {
        Meal meal = get(mealTo.getId(), userId);
        return repository.save(MealUtil.updateFromTo(meal,mealTo),AuthorizedUser.id());
    }

    @CacheEvict(value = "meals", allEntries = true)
    @Override
    public Meal save(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal, userId);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }
}
