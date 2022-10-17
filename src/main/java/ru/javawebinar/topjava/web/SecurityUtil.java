package ru.javawebinar.topjava.web;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    public static int authUserId() {
        return 1;
    }
    public static List<Integer> getAllUsers() {
        return Arrays.asList(0,1);
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}