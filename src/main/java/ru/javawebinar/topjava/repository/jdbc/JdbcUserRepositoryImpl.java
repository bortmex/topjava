package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public User save(User user) {

        List<String> roles = jdbcTemplate.queryForList("SELECT ur.role FROM user_roles ur WHERE user_id=?", String.class, user.getId());
        SortedSet<Role> rolSet = new TreeSet<>();
        roles.forEach(r -> {
            rolSet.add(Role.valueOf(r));
        });
        user.setRoles(rolSet);
        if(user.getRoles().size()==0) user.setRoles(null);

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users =  jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        addRole(users, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        addRole(users, users.get(0).getId());
        return DataAccessUtils.singleResult(users);
    }

    public List<User> addRole(List<User> users, int id){
        List<String> roles = jdbcTemplate.queryForList("SELECT ur.role FROM user_roles ur WHERE user_id=?", String.class, id);
        SortedSet<Role> rolSet = new TreeSet<>();
        roles.forEach(r -> {
            rolSet.add(Role.valueOf(r));
        });
        try {
            users.get(0).setRoles(rolSet);
        } catch (IndexOutOfBoundsException ignored){}
        return users;
    }

    @Override
    public List<User> getAll() {
        Map<Integer, Set<Role>> rolesMap = new HashMap<>();
        List<Object> usersWithRole = jdbcTemplate.query("SELECT * FROM user_roles",new RowMapper<Object>(){
            @Override
            public Object mapRow(ResultSet rs, int rownumber) throws SQLException {
                int id = rs.getInt("user_id");
                Role role = Role.valueOf(rs.getString("role"));
                if (!rolesMap.containsKey(id))
                    rolesMap.put(id, new TreeSet<Role>());
                rolesMap.get(id).add(role);
                return null;
            }
        });
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        users.forEach(u-> {int id = u.getId();
        if(rolesMap.containsKey(id)) u.setRoles(rolesMap.get(id));
        });
        return users;
    }
}
