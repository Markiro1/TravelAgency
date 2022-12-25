package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.HotelDao;
import org.project.travelagency.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(hotel);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Optional<Hotel> readById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Hotel hotel = session.get(Hotel.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.of(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Hotel> hotels = session.createQuery("from Hotel").list();
        session.getTransaction().commit();
        session.close();
        return hotels;
    }

    @Override
    public Optional<Hotel> getHotelByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Hotel hotel = session.createQuery("SELECT h FROM Hotel h WHERE h.name=: name", Hotel.class)
                .setParameter("name", name).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(hotel);
    }

    @Override
    public List<Hotel> getHotelsByCountry(String country) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Hotel> hotels = session.createQuery("from Hotel H where lower(H.country) like lower(:country)")
                .setParameter("country", country).list();
        session.getTransaction().commit();
        session.close();
        return hotels;
    }

    @Override
    public List<String> getAllCountries() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<String> countries = session.createQuery("SELECT country from Hotel").list();
        session.getTransaction().commit();
        session.close();
        return countries;
    }

    @Override
    public Hotel update(Hotel hotel) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(hotel);
        session.getTransaction().commit();
        session.close();
        return hotel;
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Hotel hotel = session.find(Hotel.class, id);
        session.delete(hotel);
        session.getTransaction().commit();
        session.close();
    }
}