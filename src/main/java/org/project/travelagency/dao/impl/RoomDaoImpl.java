package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.RoomDao;
import org.project.travelagency.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(room);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Room> readById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Room room = session.get(Room.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.of(room);
    }

    @Override
    public List<Room> getAllRooms() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Room> rooms = session.createQuery("from Room ").list();
        session.getTransaction().commit();
        session.close();
        return rooms;
    }

    @Override
    public Optional<Room> getRoomByNumber(int number) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Room room = session.createQuery("SELECT r FROM Room r WHERE r.number=: number", Room.class)
                .setParameter("number", number).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(room);
    }

    @Override
    public Room update(Room room) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(room);
        session.getTransaction().commit();
        session.close();
        return room;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Room room = session.find(Room.class, id);
        session.delete(room);
        session.getTransaction().commit();
        session.close();
    }
}