package org.project.travelagency.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.project.travelagency.model.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "org.project.travelagency")
public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.postgresql.Driver");
                settings.put(Environment.URL, "jdbc:postgresql://localhost:5432/travelagency");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");

                settings.put(Environment.USER, "postgres");
                settings.put(Environment.PASS, "root");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.FORMAT_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class)
                        .addAnnotatedClass(Hotel.class)
                        .addAnnotatedClass(Order.class)
                        .addAnnotatedClass(Room.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
