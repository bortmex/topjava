package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExceeded;

/**
 * Created by alexa on 29.01.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = MealTestData.getUpdated();
        updated.setDescription("UpdatedDescription");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, AuthorizedUser.id()));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(LocalDateTime.now(), "Ужин3000", 300);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.createWithExceed(MEAL6,true), MealsUtil.createWithExceed(MEAL5,true),
                                                                  MealsUtil.createWithExceed(MEAL4,true), MealsUtil.createWithExceed(MEAL3,false),
                                                                  MealsUtil.createWithExceed(MEAL2,false), MealsUtil.createWithExceed(MEAL1,false))));
    }

/*    @Test
    public void getBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filter?startDateTime=2015-05-29T08:00:00&endDateTime=2015-05-30T17:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MEAL2_WITH_EXCEED, MEAL1_WITH_EXCEED)));
        *//*MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
                mealService.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), AuthorizedUser.id()));*//*
    }*/

    @Test
    public void getBetween() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "filter?startDateTime=" + MEAL1.getDateTime().toString()
                + "&endDateTime=" + MEAL3.getDateTime().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(
                        getWithExceeded(Arrays.asList(MEAL3, MEAL2, MEAL1), DEFAULT_CALORIES_PER_DAY)
                )));
    }
}
