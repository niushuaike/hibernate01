package com.nsk.hibernate.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 春风再美也比不上你的笑。。。。。
 *
 * @author niushuaike
 * @create 2018/2/27
 */
public class HibernateUtil {

    private static final Configuration cfg;
    private static final SessionFactory sessionFactory;
    static {
        cfg = new Configuration().configure();
        sessionFactory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
