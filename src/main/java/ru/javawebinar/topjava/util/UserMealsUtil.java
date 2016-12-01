package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> newList =  mealList.stream().filter(s -> TimeUtil.isBetween(s.getDateTime().toLocalTime(),startTime,endTime))
                .collect(ArrayList::new, (list, item) -> list.add(new UserMealWithExceed(item.getDateTime(),item.getDescription(),item.getCalories(),getWithExceeded(mealList,item.getDateTime().toLocalDate(), caloriesPerDay))), ArrayList::addAll);
        newList.forEach(System.out::println);
        return newList;
    }

    public static boolean getWithExceeded(List<UserMeal> mealList, LocalDate localDate, int caloriesPerDay){
        Map<LocalDate, Integer> mealListMedium = mealList.stream().collect(HashMap::new,(list, item) -> list.put(item.getDateTime().toLocalDate(),0), HashMap::putAll);
        Map<LocalDate, Integer> finalMealListMedium = mealListMedium;
        mealListMedium = mealList.stream().collect(() -> (HashMap<LocalDate, Integer>) finalMealListMedium,
                (list, item) -> list.put(item.getDateTime().toLocalDate(),list.get(item.getDateTime().toLocalDate()) + item.getCalories()), HashMap::putAll);

        return mealListMedium.get(localDate)>caloriesPerDay ? Boolean.FALSE : Boolean.TRUE;
    }
}
