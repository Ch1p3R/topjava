package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.UserMealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {

    private static int id;

    public static int getId() {
        return id;
    }

    public static int getCaloriesPerDay() {
        return UserMealsUtil.DEFAULT_CALORIES_PER_DAY;
    }

    public static void setId(String id){
        int userId = 1;
        try {
            userId = Integer.parseInt(id);
        }catch (NumberFormatException e){}

        LoggedUser.id = userId;

    }
}
