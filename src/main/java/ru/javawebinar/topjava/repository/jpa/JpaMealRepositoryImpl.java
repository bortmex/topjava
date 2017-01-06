package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            return em.createNamedQuery(Meal.UPDATE).setParameter("id",meal.getId()).setParameter("datetime",meal.getDateTime()).setParameter("calories",meal.getCalories())
                    .setParameter("description",meal.getDescription()).setParameter(1, userId).executeUpdate() != 0 ? em.merge(meal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter("id", id).setParameter(1,userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return em.find(Meal.class, id).getUser().getId()==userId ? em.find(Meal.class, id) : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class).setParameter(1, userId).getResultList();

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_BETWEEN, Meal.class).setParameter("startdate", startDate).setParameter("enddate",endDate).setParameter(1,userId).getResultList();
    }
}