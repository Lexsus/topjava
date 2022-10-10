package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealsServlet extends HttpServlet {
    private List<MealTo> createMeals(){
        List<MealTo> meals = Arrays.asList(new MealTo(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500,false),
                new MealTo(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000,false),
                new MealTo(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500,true),
                new MealTo(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100,true),
                new MealTo(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000,true),
                new MealTo(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500,true),
                new MealTo(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410,true));
        return meals;

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.sendRedirect("meals.jsp");
        request.setAttribute("meals",createMeals());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
