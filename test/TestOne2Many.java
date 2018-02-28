import com.nsk.hibernate.entity.Customer;
import com.nsk.hibernate.entity.Order;
import com.nsk.hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

/**
 * 春风再美也比不上你的笑。。。。。
 *
 * @author niushuaike
 * @create 2018/2/28
 */
public class TestOne2Many {

    @Test
    public void testDelete(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Customer customer = session.get(Customer.class, "402847e961db8bb10161db8bb35f0000");
            for(Order order:customer.getOrders()){
                if (order.getProduct().contains("华")){
                    System.out.println("有华路由器！");
                }
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

    @Test
    public void testAdd(){
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Customer customer = new Customer();
            customer.setName("猪八戒");
            customer.setSex("5");

            Order order = new Order();
            order.setOrderno("001");
            order.setProduct("华硕路由器8");

            Order order2 = new Order();
            order2.setOrderno("002");
            order2.setProduct("小米路由器8");

            customer.getOrders().add(order2);
            customer.getOrders().add(order);

            session.save(customer);
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
