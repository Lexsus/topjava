package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, int idUser);

    // false if meal does not belong to userId
    boolean delete(int id, int idUser);

    // null if meal does not belong to userId
    Meal get(int id, int idUser);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int idUser);

    Collection<Meal> getAll(int idUser,LocalDateTime startDateTime, LocalDateTime endDateTime);
}
