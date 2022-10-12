package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDao implements MealDao {

    private static AtomicInteger idCount = new AtomicInteger();

    private Map<Integer, MealTo> meals;

    public InMemoryMealDao() {
        meals = new ConcurrentHashMap<>();
    }

    @Override
    public MealTo get(int id) {
        return meals.get(id);
    }

    @Override
    public void add(MealTo meal) {
        int id = idCount.getAndIncrement();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public List<MealTo> getAllMeals() {
        return new ArrayList<MealTo>(meals.values());
    }

    @Override
    public void update(MealTo meal) {
        MealTo mealUpdate = meals.get(meal.getId());
        if (mealUpdate != null) {
            meals.put(meal.getId(), meal);
        }
    }

    @Override
    public void delete(int idMeal) {
        MealTo meal = meals.get(idMeal);
        if (meal != null) {
            meals.remove(idMeal);
        }
    }
}
