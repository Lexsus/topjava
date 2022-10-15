package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UserUtils {
    public static final List<User> users  = Arrays.asList(
            new User( "Александр Иванов", "AIvanov@gmail.com", "111", Role.ADMIN),
            new User( "Сидоров Петр", "PSidorov@gmail.com", "222", Role.USER),
            new User( "Кузнецов Андрей", "AKuznezov@gmail.com", "333", Role.USER)
    );
}
