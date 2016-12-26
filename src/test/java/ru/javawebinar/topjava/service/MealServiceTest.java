package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by alexa on 25.12.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1,AuthorizedUser.id());
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1,AuthorizedUser.id());
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.get(1,AuthorizedUser.id());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDeleteForeignUsers() throws Exception {
        service.delete(MEAL1_ID,1);
    }


    @Test(expected = NotFoundException.class)
    public void testGetNotFoundForeignUsers() throws Exception {
        service.get(MEAL1_ID,1);
    }


    @Test(expected = NotFoundException.class)
    public void testUpdateNotFoundForeignUsers() throws Exception {
        service.get(MEAL1_ID,1);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateForeignUsers() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedName");
        updated.setCalories(330);
        service.update(updated,1);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID,1));
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(MEAL1_ID,AuthorizedUser.id());
        MATCHER.assertEquals(MEAL1, meal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL1_ID,AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL2), service.getAll(AuthorizedUser.id()));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> allBetween = service.getBetweenDates(LocalDate.of(2010, 6,4),LocalDate.of(2014, 6,4), AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL1), allBetween);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> allBetween = service.getBetweenDateTimes(LocalDateTime.of(2010, 6,4,7,0, 0),LocalDateTime.of(2014, 6,4,23,0, 0), AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL1), allBetween);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(AuthorizedUser.id());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1, MEAL2), all);

    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedName");
        updated.setCalories(330);
        service.update(updated,AuthorizedUser.id());
        MATCHER.assertEquals(updated, service.get(MEAL1_ID,AuthorizedUser.id()));
    }

    @Test
    public void testSave() throws Exception {

        Comparator comparator = (Comparator<Meal>) (o1, o2) -> o1.getId().compareTo(o2.getId());

        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.MAY, 30,12,34, 43), "Ужин", 1555);
        Meal created = service.save(newMeal, AuthorizedUser.id());
        newMeal.setId(created.getId());
        List<Meal> listservise = Arrays.asList(newMeal,MEAL1,MEAL2);
        List<Meal> listinspectors = (List<Meal>) service.getAll(AuthorizedUser.id());
        Collections.sort(listservise,comparator);
        Collections.sort(listinspectors,comparator);
        MATCHER.assertCollectionEquals(listservise, listinspectors);
    }

}