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
        List<UserMealWithExceed> userMealWithExceedList = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();

        System.out.println(userMealWithExceedList);
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealWithExceedFinish = new ArrayList<>();

        for (UserMeal um:mealList) {
            if(TimeUtil.isBetween(um.getDateTime().toLocalTime(),startTime,endTime)){
                userMealWithExceedFinish.add(new UserMealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),getWithExceeded(mealList,um.getDateTime().toLocalDate(), caloriesPerDay)));
            }
        }

        return userMealWithExceedFinish;
    }

    public static boolean getWithExceeded(List<UserMeal> mealList, LocalDate localDate,int caloriesPerDay){
        Map<LocalDate, Integer> mealListMedium = new HashMap<>();

        for (UserMeal um:mealList) mealListMedium.put(um.getDateTime().toLocalDate(), 0);

        for (int i = 0; i < mealList.size(); i++) {
            mealListMedium.put(mealList.get(i).getDateTime().toLocalDate(),mealListMedium.get(mealList.get(i).getDateTime().toLocalDate()) + mealList.get(i).getCalories());
        }

        return mealListMedium.get(localDate)<caloriesPerDay;
    }
}
