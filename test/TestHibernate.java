import com.nsk.hibernate.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

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
}
