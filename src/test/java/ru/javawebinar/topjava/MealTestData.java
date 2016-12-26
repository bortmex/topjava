package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final LocalDateTime ldt = LocalDateTime.now();

    public static final Meal MEAL1 = new Meal(100000, LocalDateTime.of(2016,Month.APRIL,12,34,34,34, 34542), "ужин", 100);
    public static final Meal MEAL2 = new Meal(100001, LocalDateTime.of(2016,Month.APRIL,12,34,34,34, 34542), "завтрак", 100);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>((
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories()))
                    )
    );

}
