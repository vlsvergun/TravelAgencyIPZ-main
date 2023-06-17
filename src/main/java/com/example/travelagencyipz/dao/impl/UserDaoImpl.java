package com.example.travelagencyipz.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.example.travelagencyipz.config.HibernateConfig;
import com.example.travelagencyipz.dao.UserDao;
import com.example.travelagencyipz.model.User;
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
    public User save(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.persist(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public User getById(Long id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.createQuery("SELECT u FROM User u WHERE u.email=: email", User.class)
                .setParameter("email", email).getSingleResult();
        session.getTransaction().commit();
        session.close();

        return Optional.of(user);
    }

    @Override
    public User getUserByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.createQuery("SELECT u FROM User u WHERE u.email=: email", User.class)
                .setParameter("email", email).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.update(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }
}