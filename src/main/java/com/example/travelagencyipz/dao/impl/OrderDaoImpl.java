package com.example.travelagencyipz.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.travelagencyipz.config.HibernateConfig;
import com.example.travelagencyipz.dao.OrderDao;
import com.example.travelagencyipz.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDaoImpl() {
        this.sessionFactory = HibernateConfig.getSessionFactory();
    }

    @Override
    public Order save(Order order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
        return order;
    }

    @Override
    public Order getById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Order order = session.get(Order.class, id);
        session.getTransaction().commit();
        session.close();
        return order;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Order> orders = session.createQuery("from Order").list();
        session.getTransaction().commit();
        session.close();
        return orders;
    }

    @Override
    public void delete(Order order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(order);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Order update(Order order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(order);
        session.getTransaction().commit();
        session.close();
        return order;
    }
}