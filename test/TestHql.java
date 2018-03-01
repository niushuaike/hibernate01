import com.nsk.hibernate.entity.Order;
import com.nsk.hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

/**
 * 春风再美也比不上你的笑。。。。。
 *
 * @author niushuaike
 * @create 2018/3/1
 */
public class TestHql {

    /*查询所有*/
    @Test
    public void test1(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Order> query = session.createQuery("from Order ", Order.class);

            List<Order> list = query.list();

            for (Order order:list){
                System.out.println(order);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (null!=transaction){
                transaction.rollback();
            }
        } finally {
            if (null!=session){
                session.close();
            }
            if (null!=sessionFactory){
                sessionFactory.close();
            }
        }
    }

    /*条件查询*/
    @Test
    public void test2(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Order> query = session.createQuery("from Order where orderno=?", Order.class);

            query.setParameter(0,"001");

            List<Order> list = query.list();

            for (Order order:list){
                System.out.println(order);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (null!=transaction){
                transaction.rollback();
            }
        } finally {
            if (null!=session){
                session.close();
            }
            if (null!=sessionFactory){
                sessionFactory.close();
            }
        }
    }

    /*排序*/
    @Test
    public void test3(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Order> query = session.createQuery("from Order order by orderno ", Order.class);


            List<Order> list = query.list();

            for (Order order:list){
                System.out.println(order);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (null!=transaction){
                transaction.rollback();
            }
        } finally {
            if (null!=session){
                session.close();
            }
            if (null!=sessionFactory){
                sessionFactory.close();
            }
        }
    }

    /*投影查询*/
    @Test
    public void test4(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Object[]> query = session.createQuery("select orderno , product from Order", Object[].class);

            List<Object[]> list = query.list();

            for (Object[] order:list){
                System.out.println(order[1]);
            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (null!=transaction){
                transaction.rollback();
            }
        } finally {
            if (null!=session){
                session.close();
            }
            if (null!=sessionFactory){
                sessionFactory.close();
            }
        }
    }

    /*分页查询*/
    @Test
    public void test5(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Query<Order> query = session.createQuery("from Order", Order.class);

            query.setFirstResult(1);
            query.setMaxResults(2);

            List<Order> list = query.list();

            for (Order order:list){
                System.out.println(order);
            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (null!=transaction){
                transaction.rollback();
            }
        } finally {
            if (null!=session){
                session.close();
            }
            if (null!=sessionFactory){
                sessionFactory.close();
            }
        }
    }

}
