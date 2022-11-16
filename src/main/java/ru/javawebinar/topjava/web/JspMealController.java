package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    private static final Logger log = LoggerFactory.getLogger(RootController.class);

    @Autowired
    private MealService service;

    @RequestMapping(value = {"/meals"}, method = {RequestMethod.GET})
    public String getMeals(Model model) {
        log.info("meals");
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(value = {"/meals"}, params = {"action=delete", "id"})
    public String deleteMeal(@RequestParam(value = "action") String action, @RequestParam(value = "id") Integer id, Model model) {
        log.info("delete meals");
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        return "redirect:meals";
    }

    @GetMapping(value = {"/meals"}, params = {"action=filter", "startDate", "endDate", "startTime", "endTime"})
    public String filterMeals(@RequestParam(value = "action") String action, @RequestParam(value = "startDate") String startDate,
                              @RequestParam(value = "endDate") String endDate, @RequestParam(value = "startTime") String startTime,
                              @RequestParam(value = "endTime") String endTime,
                              Model model) {
        LocalDate localStartDate = parseLocalDate(startDate);
        LocalDate localEndDate = parseLocalDate(endDate);
        LocalTime localStartTime = parseLocalTime(startTime);
        LocalTime localEndTime = parseLocalTime(endTime);

        log.info("filter meals");
        int userId = SecurityUtil.authUserId();
        LocalDateTime fromDateAndTime = LocalDateTime.of(localStartDate,
                localStartTime);
        LocalDateTime toDateAndTime = LocalDateTime.of(localEndDate,
                localEndTime);
        model.addAttribute("meals", MealsUtil.getTos(service.getBetweenInclusive(localStartDate, localEndDate, userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(value = {"/meals"}, params = {"action=update", "id"})
    public String updateMeal(@RequestParam(value = "action") String action, @RequestParam(value = "id") Integer id, Model model) {
        log.info("update meals");
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
//        service.update(meal, userId);
        model.addAttribute("meal", meal);

        return "mealForm";
    }

    @GetMapping(value = {"/meals"}, params = {"action=create"})
    public String createMeal(@RequestParam(value = "action") String action,  Model model) {
        log.info("create meals");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
//        int userId = SecurityUtil.authUserId();
//        Meal meal = service.get(id, userId);
//        service.update(meal, userId);
        model.addAttribute("meal", meal);

        return "mealForm";
    }

    @PostMapping("/meals")
    public String setMeal(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();
        Enumeration<String> names = request.getParameterNames();
        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, userId);
        } else {
            service.create(meal,userId);
        }
        return "redirect:meals";
    }

}
