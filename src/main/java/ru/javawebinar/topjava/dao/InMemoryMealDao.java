package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMealDao implements IMealDao {

    private static int idCount = 0;
    private List<MealTo> meals;

    public InMemoryMealDao() {
        meals = new ArrayList<>();
    }

    @Override
    public void addMeal(MealTo meal) {
        meal.setId(idCount++);
        meals.add(meal);
    }

    @Override
    public List<MealTo> getAllMeals() {
        return meals;
    }

    @Override
    public void updateMeal(MealTo meal) {

    }

    @Override
    public void deleteMeal(int idMeal) {
        int index = getMealIndex(idMeal);
        if (index > 0) {
            meals.remove(index);
        }
    }

    private int getMealIndex(int idMeal) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).getId() == idMeal) {
                return i;
            }
        }
        return -1;
    }
}
