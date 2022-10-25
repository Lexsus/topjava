package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAT_ID = START_SEQ + 3;
    public static final int SOUP_ID = START_SEQ + 4;
    public static final int PANCAKES_ID = START_SEQ + 5;

    public static final Meal meat = new Meal(MEAT_ID, LocalDateTime.of(2015, 1, 10, 0, 51, 14), "meat", 100);
    public static final Meal soup = new Meal(SOUP_ID, LocalDateTime.of(2015, 1, 11, 0, 51, 14), "soup", 300);
    public static final Meal pancakes = new Meal(PANCAKES_ID, LocalDateTime.of(2015, 1, 10, 0, 51, 14), "pancakes", 500);

    public static Meal getUpdated() {
        Meal updated = new Meal(meat);
        updated.setDateTime(LocalDateTime.of(2015, 1, 10, 0, 51, 14));
        updated.setDescription("UpdatedName");
        updated.setCalories(330);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2016, 1, 10, 0, 51, 14), "hamburger", 100);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
