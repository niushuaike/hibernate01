# hibernate01
hibernate Java项目 基本学习
## 一、基本配置
### 1、导入jar包
        lib中required所有jar包和数据库对应jar包
### 2、全局配置文件hibernate.cfg.xml 
```
<?xml version="1.0" encoding="UTF-8"?>
<!--文档说明：标记约束文档 -->
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<!--Hibernate的配置信息 -->
<hibernate-configuration>
    <!--配置工厂信息，全局信息 -->
    <session-factory>
        <!--1、设置四本一言  -->
        <!--四本一言 四大基本项： 1、驱动类名 2、指明需要连接的url 3、用户名 4、密码 Hibernate支持不同的数据库，但是每种数据库语法可能有区别，可以使用方言,注意版本 -->

        <!--数据库驱动类全称  -->
        <property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
        <!--数据库url地址  -->
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/hibernate_db?characterEncoding=UTF-8</property>
        <!--用户名  -->
        <property name="hibernate.connection.username">root</property>
        <!--密码  -->
        <property name="hibernate.connection.password">root</property>
        <!--方言 -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>

        <!--2、全局配置信息  -->
        <!--执行DDL的类别：
        create:每次都删除新建
        update:存在就修改，不存在就新建  -->
        <property name="hibernate.hbm2ddl.auto">update</property>
        <!--是否显示SQL语句  -->
        <property name="hibernate.show_sql">true</property>
        <!--是否格式化SQL语句  -->
        <property name="hibernate.format_sql">true</property>

        <!-- 启用getCurrentSession，默认未启用 -->
        <property name="hibernate.current_session_context_class">thread</property>

        <!--3、加载配置文件  -->
        <!--基于xml映射文件： 映射文件加载。路径 -->
        <mapping resource="com/nsk/hibernate/entity/User.hbm.xml"></mapping>
    </session-factory>
</hibernate-configuration>
```
### 3、编写实体

### 4、编写实体表映射
    默认与实体同一目录
   ```
    <?xml version="1.0" encoding="UTF-8"?>
    <!--文档说明，设置映射文件  -->
    <!DOCTYPE hibernate-mapping
            SYSTEM
            "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd" >
    <!--映射标签
     package：内部类的所在的包名 -->
    <hibernate-mapping package="com.nsk.hibernate.entity">
        <!--需要设置的映射类：设置该类对应的表  -->
        <!--属性：
        name:类名
        table:表名  -->
        <class name="User" table="tb_user">
            <!--id:主键，name:属性名称，column：字段名称  -->
            <id name="id" column="id">
                <!--generator:主键生成策略
                class:标记主键如何生成
                取值：
                1、native：自动增长，会根据当前的数据库自动切换
                2、identity：mySQL的自增策略
                3、sequence：Oracle的自增标记
                4、uuid:32位字符串
                5、assigned:自定义字符串
                6、foreign：外键
                7、increment：自己维护自增关系
                  -->
                <generator class="native"/>
            </id>
            <!--配置属性对应的字段  -->
            <property name="name" length="16" column="username"/>
            <property name="pass" length="32"/>
        </class>
    </hibernate-mapping>
   ```
 #### 5、测试
 ```    
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
   ```
