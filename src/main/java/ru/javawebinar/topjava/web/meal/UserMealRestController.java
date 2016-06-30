package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    private UserMealService service;
    private static final Logger LOG = getLogger(UserMealRestController.class);

    public UserMeal get(int id){
        int userId = LoggedUser.id();
        LOG.info("get meal {} for user {}", id, userId);
        return service.get(userId, id);
    }

    public void delete(int id){
        int userId = LoggedUser.id();
        LOG.info("user {} deleted meal {}",userId, id);
        service.delete(userId, id);
    }

    public UserMeal update(UserMeal meal){
        int userId = LoggedUser.id();
        LOG.info("update {} for user", meal, userId);
        service.save(userId, meal);
        return meal;
    }

    public Collection<UserMealWithExceed> getAll(){
        int userId = LoggedUser.id();
        LOG.info("get all for user {}", userId);
        return UserMealsUtil.getFilteredWithExceeded(
                service.getAll(userId), LocalTime.MIN, LocalTime.MAX, LoggedUser.getCaloriesPerDay());
    }

    public Collection<UserMealWithExceed> getBetween(LocalDate startDate, LocalTime startTime,
                                                     LocalDate endDate, LocalTime endTime){
        int userId = LoggedUser.id();
        LOG.info("get between dates {} - {} and between time {} - {} for user {}", startDate, endDate, startTime, endTime, userId);
        return UserMealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate, endDate, userId), startTime, endTime, LoggedUser.getCaloriesPerDay());
    }

}
