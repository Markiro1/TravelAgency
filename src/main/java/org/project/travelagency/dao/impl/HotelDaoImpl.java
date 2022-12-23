package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.HotelDao;
import org.project.travelagency.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HotelDaoImpl implements HotelDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public HotelDaoImpl() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
    }

    @Override
    public void create(Hotel hotel) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(hotel);
        transaction.commit();
    }

    @Override
    public Optional<Hotel> readById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Hotel hotel = session.get(Hotel.class, id);
            return Optional.of(hotel);

        } catch (NullPointerException exp) {
            return Optional.empty();

        } finally {
            transaction.commit();
        }
    }

    @Override
    public List<Hotel> getAllHotels() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            return session.createQuery("from Hotel h").getResultList();

        } catch (NullPointerException e) {
            return new ArrayList<>();

        } finally {
            transaction.commit();
        }
    }

    @Override
    public Optional<Hotel> getHotelByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        try {
            Query query = session
                    .createQuery("FROM Hotel H WHERE H.name = :name")
                    .setParameter("name", name);

            if (query.getResultList().isEmpty()) {
                return Optional.empty();
            }
            return Optional.of((Hotel) query.getResultList().get(0));

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
        Hotel hotel = session.find(Hotel.class, id);
        session.remove(hotel);
        transaction.commit();
    }
}