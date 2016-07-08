package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static UserMeal MEAL_ID1 = new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static UserMeal MEAL_ID2 = new UserMeal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static UserMeal MEAL_ID3 = new UserMeal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static UserMeal MEAL_ID4 = new UserMeal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static UserMeal MEAL_ID5 = new UserMeal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static UserMeal MEAL_ID6 = new UserMeal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);


}
