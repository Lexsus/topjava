package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Integer> mealsOfUsers = new ConcurrentHashMap<>();

//    {
//        MealsUtil.meals.forEach(it->save(it,1));
//    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            mealsOfUsers.put(meal.getId(), userId);
            return meal;
        }
        if (isAuthUser(meal.getId(), userId)) {
            throw new NotFoundException("unauthorized user");
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (isAuthUser(id, userId)) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (isAuthUser(id, userId)) return null;
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(it -> isAuthUser(it, userId))
                .sorted(Comparator.comparing(Meal::getDate).reversed())//TODO  проверить сортировку
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private List<Meal> filterByPredicate(Predicate<Meal> filter) {
        return repository.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed())//TODO  проверить сортировку
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    //TODO  проверить работу
    @Override
    public List<Meal> getFilteredAll(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDateTime newStartDateTime = startDateTime.with(LocalTime.of(0, 0, 0));
        LocalDateTime newEndDateTime = endDateTime.with(LocalTime.of(23, 59, 59));
        return filterByPredicate(meal -> isAuthUser(meal, userId) && DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), newStartDateTime, newEndDateTime));
    }

    private boolean isAuthUser(Meal meal, int idUser) {
        Integer ownerId = mealsOfUsers.get(meal.getId());
        return !((ownerId == null) || (!ownerId.equals(idUser)));
    }

    private boolean isAuthUser(int id, int userId) {
        Integer ownerId = mealsOfUsers.get(id);
        if ((ownerId == null) || (!ownerId.equals(userId)))
            return true;
        return false;
    }
}

