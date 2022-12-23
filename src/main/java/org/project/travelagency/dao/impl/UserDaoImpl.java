package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.UserDao;
import org.project.travelagency.model.Order;
import org.project.travelagency.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        return user;
    }

    @Override
    public void delete(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        if (currentSession.isOpen()) currentSession.close();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        return user;
    }

    @Override
    public User getById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        return session.get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        return session.createQuery("from User").list();
    }
}
