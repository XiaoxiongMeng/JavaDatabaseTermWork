/**
 * @author 32808
 * 第一步：注册驱动	作用：告诉Java程序，即将要连接的是哪个品牌的数据库
 * 第二步：获取连接	表示JVM的进程和数据库进程之间的通道打开了，这属于进程之间的通信使用完之后一定要关闭通道
 * 第三步：获取数据库操作对象	专门执行sql语句的对象
 * 第四步：执行SQL语句	DQL DML…
 * 第五步：处理查询结果集	只有当第四步执行的是select语句的时候，才有这第五步处理查询结果集
 * 第六步：释放资源	使用完资源之后一定要关闭资源。Java和数据库属于进程间的通信，开启之后一定要关闭
 */


import java.sql.*;
import src.LoginForm;


public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {

            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            //1、注册驱动 连接mysql


            String db_url = "jdbc:mysql://127.0.0.1:3306/jdbc?useSSL=false";
            String db_user = "root";
            String db_password = "123456";
            conn = DriverManager.getConnection(db_url,db_user,db_password);
            //2、获取连接
            //127.0.0.1是本地ip地址


            stmt = conn.createStatement();
            //3、获取数据库操作对象  statement用于执行sql语句


        } catch (SQLException e) {
            e.printStackTrace();
        }

        new LoginForm(stmt,conn);


    }
}