package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.RoomDao;

import org.project.travelagency.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RoomDaoImpl implements RoomDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public RoomDaoImpl() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
    }

    @Override
    public void create(Room room) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(room);
        transaction.commit();
    }

    @Override
    public Optional<Room> readById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Room room = session.get(Room.class, id);
            return Optional.of(room);

        } catch (NullPointerException exp) {
            return Optional.empty();

        } finally {
            transaction.commit();
        }
    }

    @Override
    public List<Room> getAllRooms() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            return session.createQuery("from Room r").getResultList();

        } catch (NullPointerException e) {
            return new ArrayList<>();

        } finally {
            transaction.commit();
        }
    }

    @Override
    public Optional<Room> getRoomByNumber(int number) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session
                    .createQuery("FROM Room R WHERE R.number = :number")
                    .setParameter("number", number);

            if (query.getResultList().isEmpty()) {
                return Optional.empty();
            }
            return Optional.of((Room) query.getResultList().get(0));

        } catch (NullPointerException exp) {
            return Optional.empty();

        } finally {
            transaction.commit();
        }
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Room room = session.find(Room.class, id);
        session.remove(room);
        transaction.commit();
    }
}