package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500), new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        List<UserMealWithExcess> mealsToOptional = filteredByCyclesOptional(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToOptional.forEach(System.out::println);

        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
    }

    /**
     * Get a filtered list of meals (implementation via loops)
     *
     * @param meals          list of meals
     * @param startTime      search time start
     * @param endTime        search end start
     * @param caloriesPerDay maximum calories per day
     * @return filtered list of meals
     */
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : meals) {
            mapCaloriesPerDay.put(meal.getDateTime().toLocalDate(), mapCaloriesPerDay.getOrDefault(meal.getDateTime().toLocalDate(),
                    0) + meal.getCalories());
        }

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(),
                        mapCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return mealsWithExcess;
    }

    /**
     * Get a filtered list of meals (implementation via streams)
     *
     * @param meals          list of meals
     * @param startTime      search time start
     * @param endTime        search end start
     * @param caloriesPerDay maximum calories per day
     * @return filtered list of meals
     */
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesMap = meals.stream()
                .collect(Collectors.groupingBy(meal ->meal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(meal ->TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    /**
     * Get a filtered list of meals in one pass(implementation via loops)
     *
     * @param meals          list of meals
     * @param startTime      search time start
     * @param endTime        search end start
     * @param caloriesPerDay maximum calories per day
     * @return filtered list of meals
     */
    public static List<UserMealWithExcess> filteredByCyclesOptional(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        Map<LocalDate, Integer> mapCaloriesPerDay = new HashMap<>();
        internalFilteredByCyclesOptional(mapCaloriesPerDay, meals, 0, startTime, endTime, caloriesPerDay, mealsWithExcess);
        return mealsWithExcess;
    }
    private static void internalFilteredByCyclesOptional(Map<LocalDate, Integer> mapCaloriesPerDay, List<UserMeal> meals, int index, LocalTime startTime, LocalTime endTime, int caloriesPerDay, List<UserMealWithExcess> mealsWithExcess) {
        if (index < meals.size()) {
            UserMeal meal = meals.get(index);
            mapCaloriesPerDay.put(meal.getDateTime().toLocalDate(),
                    mapCaloriesPerDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories());
            internalFilteredByCyclesOptional(mapCaloriesPerDay, meals, ++index, startTime, endTime, caloriesPerDay,
                    mealsWithExcess);

            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(),
                        mapCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
    }
}
