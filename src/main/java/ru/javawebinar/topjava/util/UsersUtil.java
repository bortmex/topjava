package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rogov on 16.12.2016.
 */
public class UsersUtil {
    public static final List<User> USERS = Arrays.asList( new User(1, "Алексей", "email1", "password", Role.ROLE_ADMIN),
            new User(2, "userName2", "email2", "password", Role.ROLE_USER),
            new User(3, "userName3", "email3", "password", Role.ROLE_USER),
            new User(4, "userName4", "email4", "password", Role.ROLE_USER));
}
