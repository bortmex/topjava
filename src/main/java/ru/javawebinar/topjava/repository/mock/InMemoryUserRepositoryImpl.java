package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UsersUtil.USERS.forEach(this::save);
    }


    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
            return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        List<User> listUser = new ArrayList<>(repository.values());
        Collections.sort(listUser, (a, b) -> a.getName().compareTo(b.getName()));
        return listUser;
    }

    @Override
    public User getByEmail(String email) {
        User user;
        try {
            user =  repository.entrySet().stream().filter(s -> s.getValue().getEmail().equals(email)).findFirst().get().getValue();
        }catch (NoSuchElementException e){
            throw new NotFoundException("Не найдено");
        }
        return user;
    }
}
