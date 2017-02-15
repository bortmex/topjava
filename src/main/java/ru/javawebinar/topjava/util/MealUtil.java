package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

/**
 * Created by alexa on 13.02.2017.
 */
public class MealUtil {

    public static Meal createNewFromTo(Meal newMeal) {
        return new Meal(null, newMeal.getDateTime(), newMeal.getDescription(), newMeal.getCalories());
    }

    public static Meal asTo(Meal meal) {
        return new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    public static Meal updateFromTo(Meal meal, Meal mealTo) {
        meal.setDateTime(mealTo.getDateTime());
        meal.setDescription(mealTo.getDescription());
        meal.setCalories(mealTo.getCalories());
        return meal;
    }

}
