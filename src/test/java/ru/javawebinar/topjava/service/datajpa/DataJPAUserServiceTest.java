package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AllServicesTest;

import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by ColdDeath&Dummy on 17.01.2017.
 */
@ActiveProfiles(Profiles.DATA_JPA)
public class DataJPAUserServiceTest extends AllServicesTest
{
    @Test
    public void testGetUserWithMeals() throws Exception
    {
        User user = userService.getWithMeals(USER_ID);
        Collection<Meal> actual = user.getMeals();
        MealTestData.MEAL_MODEL_MATCHER.assertCollectionEquals(Arrays.asList(
                MealTestData.MEAL1, MealTestData.MEAL2, MealTestData.MEAL3, MealTestData.MEAL4, MealTestData.MEAL5, MealTestData.MEAL6), actual);
    }
}
