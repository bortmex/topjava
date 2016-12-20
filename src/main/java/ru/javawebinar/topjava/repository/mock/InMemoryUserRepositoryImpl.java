package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

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
        LOG.info("delete " + id);
        if(repository.containsKey(id)){
            repository.remove(id);
        return true;}
        else return false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);if(repository.containsKey(id)){
            return repository.get(id);}
        else return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");

        List<User> listUser = new ArrayList<>();
        for (Map.Entry<Integer,User> user:repository.entrySet()) {
            listUser.add(user.getValue());
        }

        Collections.sort(listUser, (a, b) -> a.getName().compareTo(b.getName()));
        return listUser;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        for (Map.Entry<Integer,User> user:repository.entrySet()) {
            if(user.getValue().getEmail().equals(email)) return user.getValue();
        }
        return null;
    }
}
