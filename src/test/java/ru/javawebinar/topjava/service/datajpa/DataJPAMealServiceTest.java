package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AllServicesTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

/**
 * Created by ColdDeath&Dummy on 17.01.2017.
 */
@ActiveProfiles(Profiles.DATA_JPA)
public class DataJPAMealServiceTest extends AllServicesTest
{
    @Test
    public void testGetWithUser() throws Exception {
        Meal meal = mealService.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        UserTestData.USER_MODEL_MATCHER.assertEquals(UserTestData.ADMIN, meal.getUser());
    }
}
