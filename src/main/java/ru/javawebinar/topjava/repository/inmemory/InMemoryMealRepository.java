package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Integer> mealsOfUsers = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(it->save(it,1));
    }

    @Override
    public Meal save(Meal meal, int idUser) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            mealsOfUsers.put(meal.getId(),idUser);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id,int idUser) {
        Integer user = mealsOfUsers.get(id);
        if ((user==null) || (user.equals(idUser)))
            return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int idUser) {
        Integer user = mealsOfUsers.get(id);
        if ((user==null) || (user.equals(idUser)))
            return null;
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDate))//TODO  проверить сортировку
                .collect(Collectors.toList());
    }

    private List<Meal> filterByPredicate(Predicate<Meal> filter){
        return repository.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate))//TODO  проверить сортировку
                .collect(Collectors.toList());
    }

    //TODO  проверить работу
    @Override
    public Collection<Meal> getAll(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return filterByPredicate(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }
}

