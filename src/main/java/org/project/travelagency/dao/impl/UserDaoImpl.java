package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.UserDao;
import org.project.travelagency.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = session.createQuery("SELECT u FROM User u WHERE u.email=: email", User.class)
                .setParameter("email", email).getSingleResult();
        session.getTransaction().commit();

        return Optional.of(user);
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction();
    }
}
