package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceedList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
// .toLocalDate();
// .toLocalTime();

        System.out.println(userMealWithExceedList);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mealListMedium = mealList
                .stream()
                .collect(HashMap<LocalDate, Integer>::new, (m, c) -> m.put(c.getDateTime().toLocalDate(), 0), (m, u) -> {
                });

        mealList
                .stream()
                .collect(() -> mealListMedium, (list, item) -> list.put(item.getDateTime().toLocalDate(), list.get(item.getDateTime().toLocalDate()) + item.getCalories()), Map::putAll);

        List<UserMealWithExceed> userMealWithExceedMedium = mealList
                .stream()
                .collect(ArrayList<UserMealWithExceed>::new, (m, c) -> m.add(new UserMealWithExceed(c.getDateTime(), c.getDescription(), c.getCalories(), mealListMedium.get(c.getDateTime().toLocalDate()) < caloriesPerDay)), (m, u) -> {
                });


        return userMealWithExceedMedium
                .stream()
                .filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime))
                .collect(ArrayList<UserMealWithExceed>::new, ArrayList::add, (m, u) -> {
                });
    }

}