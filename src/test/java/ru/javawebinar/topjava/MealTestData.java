package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.*;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static List<Meal> sortMeals(List<Meal> list){
        Collections.sort(list,(o1, o2) -> o1.getId().compareTo(o2.getId()));
        return list;
    }

    public static final int MEAL1_ID = START_SEQ;
    public static final int MEAL2_ID = START_SEQ + 1;

    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2012, 6,4,15,0, 0), "ужин", 100);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.of(2015, 6,4,10,0, 0), "завтрак", 100);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>((
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories()))
                    )
    );

}
