package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {
        User ref = em.getReference(User.class, userId);
        userMeal.setUser(ref);
        if (userMeal.isNew()){
            em.persist(userMeal);
        }
        else{
            if (get(userMeal.getId(), userId) == null) return null;
            em.merge(userMeal);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(UserMeal.DELETE, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> meal = em.createNamedQuery(UserMeal.GET, UserMeal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList();
        return meal.size() == 0 ? null : DataAccessUtils.singleResult(meal);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return em.createNamedQuery(UserMeal.GETALL, UserMeal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GETBETWEEN, UserMeal.class)
                .setParameter("user_id", userId)
                .setParameter("after", startDate)
                .setParameter("before", endDate)
                .getResultList();
    }
}