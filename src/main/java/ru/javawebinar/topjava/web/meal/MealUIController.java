package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping(value = "ui/meals", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealUIController extends AbstractMealController {

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @RequestParam   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime dateTime,
            @RequestParam String description,
            @RequestParam int calories) {
        super.create(new Meal(dateTime, description, calories));
    }

    @GetMapping("/filter")
    public List<MealTo> getBetween(
            @RequestParam @Nullable LocalDate startDate,
            @RequestParam @Nullable LocalDate endDate,
            @RequestParam @Nullable LocalTime startTime,
            @RequestParam @Nullable LocalTime endTime,
            @RequestParam @Nullable String unused) {  // unused это типа заглушка чтобы принять паразитный параметр
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}