import com.nsk.hibernate.entity.User;
import com.nsk.hibernate.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;

/**
 * 春风再美也比不上你的笑。。。。。
 *
 * @author niushuaike
 * @create 2018/2/26
 */
public class TestHibernate {

    @Test
    public void test1() {
        // 1、加载配置文件
        Configuration configuration = new Configuration().configure();
        // 2、创建Session工厂
        SessionFactory factory = configuration.buildSessionFactory();
        // 3、创建Session对象
        Session session = factory.openSession();
        // 4、操作数据
        System.out.println(session.save(new User("测试", "666666")));
        // 5、关闭
        session.close();
    }

    /*
    *    1、事务的完善写法
     *   2、验证一级缓存
     *   3、验证自动修改持久态对象，快照区的存在
    */
    @Test
    public void testCache() {
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, 1);
            System.out.println(user);
            user.setName("西方不败");
            User user1 = session.get(User.class, 1);
            System.out.println(user1);
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
        }
    }

    @Test
    public void testQuery() {
        SessionFactory sessionFactory=null;
        Session session=null;
        Transaction transaction=null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("from User ", User.class);
            List<User> list = query.list();
            for(User user:list){
                System.out.println(user);
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
        }
    }
}
