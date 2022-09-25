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

        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    /**
     * Получить отфильтрованный список приемов пищи (реализация через циклы)
     * @param meals список приемов пищи
     * @param startTime начало времени поиска
     * @param endTime конец времени поиска
     * @param caloriesPerDay максимальная величина калорий в день
     * @return отфильтрованный список приемов пищи
     */
    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
//          Collections.sort(meals,(o1, o2) -> o1.getDateTime().compareTo(o1.getDateTime()));
        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        Map<LocalDate, Integer> calories = new HashMap<>();
        for (UserMeal meal : meals) {
            if (calories.containsKey(meal.getDateTime().toLocalDate())) {
                //calories.merge()
                int calorieDay = calories.get(meal.getDateTime().toLocalDate());
                calorieDay += meal.getCalories();
                calories.put(meal.getDateTime().toLocalDate(), calorieDay);
            } else {
                calories.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            }
        }
        for (UserMeal meal : meals) {
            calories.put(meal.getDateTime().toLocalDate(),calories.getOrDefault(meal.getDateTime().toLocalDate(),0)+meal.getCalories());
        }
        for (UserMeal meal : meals) {
            calories.put(meal.getDateTime().toLocalDate(),calories.getOrDefault(meal.getDateTime().toLocalDate(),0)+meal.getCalories());
        }

        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        isExcess(calories, meal.getDateTime().toLocalDate(), caloriesPerDay)));
            }
        }


        return mealsWithExcess;
    }

    private static boolean isExcess(Map<LocalDate, Integer> caloriesMap, LocalDate date, int caloriesPerDay) {
        return caloriesMap.get(date) > caloriesPerDay;
    }

    /**
     * Получить отфильтрованный список приемов пищи (реализация через стримы)
     * @param meals список приемов пищи
     * @param startTime начало времени поиска
     * @param endTime конец времени поиска
     * @param caloriesPerDay максимальная величина калорий в день
     * @return отфильтрованный список приемов пищи
     */
    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        Map<LocalDate, Integer> map = meals.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        List<UserMealWithExcess> list = meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        isExcess(map, meal.getDateTime().toLocalDate(), caloriesPerDay)))
                .collect(Collectors.toList());
        return list;
    }
}
