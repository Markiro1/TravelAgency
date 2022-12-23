package org.project.travelagency.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.project.travelagency.config.HibernateConfig;
import org.project.travelagency.dao.HotelDao;
import org.project.travelagency.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HotelDaoImpl implements HotelDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public HotelDaoImpl() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
    }

    @Override
    public Hotel create(Hotel hotel) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(hotel);
        transaction.commit();
        return hotel;
    }

    @Override
    public Hotel readById(Long id) {
        return null;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return null;
    }

    @Override
    public Hotel getHotelByName(String name) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
