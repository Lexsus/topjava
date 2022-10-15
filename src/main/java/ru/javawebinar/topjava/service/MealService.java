package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int idUser) {
        return repository.save(meal, idUser);
    }

    public void delete(int id,int idUser) {
        checkNotFoundWithId(repository.delete(id, idUser), id);
    }

    public Meal get(int id,int idUser) {
        return checkNotFoundWithId(repository.get(id, idUser),id);
    }

    //TODO нужна ли фтльтрация по юзеру?
    public List<Meal> getAll() {
        return (List<Meal>) repository.getAll();
    }

    public Collection<Meal> getAll(LocalDateTime startDateTime, LocalDateTime endDateTime){
        return (List<Meal>) repository.getAll(startDateTime, endDateTime);
    }

    public void update(Meal meal, int idUser) {
        checkNotFoundWithId(repository.save(meal, idUser), meal.getId());
    }

}