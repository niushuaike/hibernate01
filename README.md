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
  ## 二、主要接口解释
  ### 1、Configuration
  ```
  Configuration 类负责读取主配置文件的信息。包括如下内容：
    *加载Xml文件（hibernate.cfg.xml）创建对象方式 
    *根据hibernate.cfg.xml文件加载持久化类与数据表的映射关系文件（*.hbm.xml）
    Hibernate运行的底层信息：数据库的URL、用户名、密码、JDBC驱动类，数据库Dialect,数据库连接池等（对应 hibernate.cfg.xml 文件）。
  Configuration cfg = new Configuration().configure();
  ```
  ### 2、SessionFactory
    Configuration对象根据当前的配置信息生成SessionFactory对象。（该对象中包含了数据库配置信息和所有映射关系以及预定义的SQL语句）
    该对象还负责维护Hibernate的二级缓存
   ```
   获取SessionFactory的方式：
   
      Configuration cfg = new Configuration().configure();
   
      SessionFactory sf = cfg.buildSessionFactory();
   
   SessionFactory作用：是生成Session的工厂
   
       Session session = sf.openSession(); 
   ```
  ### 3、Transaction
    代表一次数据库访问的最小操作，具有数据库事务的概念。所有持久层都应该在事务管理下进行，即使是只读操作。
    获取Transaction对象的方式：
      Transaction tx =session.beginTransaction();
    常用方法:
    commit():提交相关联的session实例
    rollback():撤销事务操作
    wasCommitted():检查事务是否提交
  ### 4、Query
    Query接口 ：封装数据库查询的返回结果(hql的查询方式)
    获取Query对象的方式：
     Query query = session.createQuery(“hql");
    常用方法
    Iterator():获取查询结果的迭代器对象（只查询主键，返回）
    list():以集合的方式获取查询结果
  ### 5、执行顺序
    a、应用程序先调用Configuration类,该类读取Hibernate配置文件及映射文件中的信息，
    b、并用这些信息生成一个SessionFactory对象，
    c、然后从SessionFactory对象生成一个Session对象，
    d、并用Session对象生成Transaction对象；
    e、可通过Session对象的get(),load(),save(),update(),delete()和saveOrUpdate()、createQuery()等方法对进行CURD等操作；
    f、提交事物
 ## 三、实体类的三种状态
    1、瞬时态
        与session无关联
        对象中无ID
        一般用于保存
    2、持久态
        与session有关联
        对象中有ID
        一般指session查出来的对象
        
    3、托管态
        与session无关联
        对象中有ID
 ## 四、缓存
   ### 1、一级缓存
        （1）默认是打开的
        （2）使用范围为session
        （3）存储的数据为持久态数据
         注意：（基于快照的机制）对该持久态数据做修改，会在提交事务的时候自动更新数据库而不需要调用update方法
            当session.get查出持久太对象后，hibernate会将数据存一份进入缓存，存一份进入快照区，
            当我们修改持久太对象时，会修改缓存中对应的数据内容，但是不会修改快照区中的内容
            当事务提交时，会对比缓存和快照区中的内容是否一致，若不一致则更新数据库内容
   ### 2、二级缓存
        （1）目前已不使用，使用Redis替代
        （2）默认不是打开，需要配置
        （3）适用范围为sessionfactory
 ## 五、关联关系配置
   ### 1、一对多
        1）、在一上，对象中增加set属性，在配置文件中僧伽set配置
        2）、在多上，对象中增加一的对象属性，在配置文件中配置many to one属性
        3）、配置级联插入、删除，于一的配置文件set属性中，配置属性cascade值save-update，delete
        4）、执行过程
            ①、先查主表
            ②、根据主表id，即子表外键，查询子表
            ③、设置子表外键为null
            ④、删除主表
            ⑤、删除子表
   ### 2、多对多
