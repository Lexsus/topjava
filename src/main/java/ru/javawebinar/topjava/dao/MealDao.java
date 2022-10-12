package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDao {

    /**
     * куегкт meal from id
     *
     * @param id
     * @return
     */
    MealTo get(int id);

    /**
     * Add meal to the list
     *
     * @param meal
     */
    void add(MealTo meal);

    /**
     * Get all meals
     */
    List<MealTo> getAllMeals();

    /**
     * Update meal in the list
     *
     * @param meal
     */
    void update(MealTo meal);

    /**
     * Delete meal from list
     *
     * @param idMeal Id of meal
     */
    void delete(int idMeal);
}
