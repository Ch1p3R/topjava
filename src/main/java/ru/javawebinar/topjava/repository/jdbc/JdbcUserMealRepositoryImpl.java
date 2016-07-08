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
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> MEAL_ROW_MAPPER = new RowMapper<UserMeal>() {
        @Override
        public UserMeal mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserMeal(rs.getInt("id"), rs.getTimestamp("date_time").toLocalDateTime(), rs.getString("description"),
                    rs.getInt("calories"));
        }
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    public JdbcUserMealRepositoryImpl() {
    }

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("user_id", userId)
                .addValue("date_time", userMeal.getDateTime())
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());
        if (userMeal.isNew()){
            simpleJdbcInsert.execute(map);
        }
        else {
            namedParameterJdbcTemplate.update("UPDATE meals SET date_time=:date_time, description=:description," +
                    "calories=:calories WHERE id=:id AND user_id=:user_id", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", MEAL_ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=?", MEAL_ROW_MAPPER, userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? AND date_time > ? AND meals.date_time < ? " +
                "ORDER BY date_time", MEAL_ROW_MAPPER, userId, startDate, endDate);
    }
}
