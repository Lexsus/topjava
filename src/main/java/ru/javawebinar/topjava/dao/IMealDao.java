package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface IMealDao {
    /**
     * Add meal to the list
     *
     * @param meal
     */
    public void addMeal(MealTo meal);

    /**
     * Get all meals
     */
    public List<MealTo> getAllMeals();

    /**
     * Update meal in the list
     *
     * @param meal
     */
    public void updateMeal(MealTo meal);

    /**
     * Delete meal from list
     *
     * @param idMeal Id of meal
     */
    public void deleteMeal(int idMeal);
}
