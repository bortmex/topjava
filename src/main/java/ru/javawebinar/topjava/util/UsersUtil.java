package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rogov on 16.12.2016.
 */
public class UsersUtil {
    public static final List<User> USERS = Arrays.asList( new User(1, "userName", "email", "password", Role.ROLE_ADMIN),
            new User(2, "userName", "email", "password", Role.ROLE_USER),
            new User(3, "userName", "email", "password", Role.ROLE_USER),
            new User(4, "userName", "email", "password", Role.ROLE_USER));
}
