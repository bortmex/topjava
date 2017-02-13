package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

/**
 * Created by alexa on 13.02.2017.
 */
public class MealUtil {

    public static Meal createNewFromTo(MealTo newMeal) {
        return new Meal(null, newMeal.getDateTime(), newMeal.getDescription(), newMeal.getCalories());
    }

    public static MealTo asTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
    }

    public static Meal updateFromTo(Meal meal, MealTo mealTo) {
        meal.setDateTime(mealTo.getDateTime());
        meal.setDescription(mealTo.getDescription());
        meal.setCalories(mealTo.getCalories());
        return meal;
    }

}
