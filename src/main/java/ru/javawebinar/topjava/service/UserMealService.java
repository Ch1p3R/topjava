package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


public interface UserMealService {

    UserMeal save(int userId, UserMeal userMeal);

    void delete(int userId, int id) throws NotFoundException;

    UserMeal get(int userId, int id) throws NotFoundException;

    Collection<UserMeal> getAll(int userId);

    default Collection<UserMeal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId){
        return getBetweenDateTimes(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX), userId);
    }

    Collection<UserMeal> getBetweenDateTimes(LocalDateTime startDate, LocalDateTime endDate, int userId);


}
