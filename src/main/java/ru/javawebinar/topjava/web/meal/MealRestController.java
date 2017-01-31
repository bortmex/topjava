package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @Override
    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}")
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setLocation(uriOfNewResource);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @GetMapping()
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }


/*    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDateTiem, @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDateTime) {
        return super.getBetween(startDateTiem.toLocalDate(), startDateTiem.toLocalTime(), endDateTime.toLocalDate(), endDateTime.toLocalTime());
    }*/

    @GetMapping(value = "/filter")
    public List<MealWithExceed> getBetween(@RequestParam("startDateTime") LocalDateTime startDateTiem, @RequestParam("endDateTime") LocalDateTime  endDateTime) {
        return super.getBetween(startDateTiem.toLocalDate(),
                startDateTiem.toLocalTime(),
                endDateTime.toLocalDate(),
                endDateTime.toLocalTime());
    }
}
