package src;

import java.sql.*;
import java.util.Objects;

/**
 * @author 32808
 * ;
 */
public class Database {
    public class Post{
        public int tid;
        public String title;
        public int uid;
        public String content;

        public Post(int tid, String title, String content,int uid) {
            this.tid = tid;
            this.title = title;
            this.uid = uid;
            this.content = content;
        }
    }

    public class Bill{
        public int bid;
        public String title;
        public int uid;
        public String content;
        public double amount;

        public Bill(int bid, String title, String content,int uid, double amount) {
            this.bid = bid;
            this.title = title;
            this.uid = uid;
            this.content = content;
            this.amount = amount;
        }
    }


    public class User{
        public int uid;
        public String username;
        public int post;
        public String shows;
        public double balance;

        public User(int uid, String username, String shows, int post, double balance) {
            this.post = post;
            this.username = username;
            this.uid = uid;
            this.shows = shows;
            this.balance = balance;
        }
    }



    public static int getNewUserId(Statement stmt, Connection conn){
        int id = 0;
        try {
            String sql = "CALL GetNewUserId();";
            ResultSet a = stmt.executeQuery(sql);
            while (a.next()){
                id = a.getInt(1);
                break;
            }
        return id+1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    // 获取用户的唯一辨识id


    public static int getNewPostId(Statement stmt, Connection conn){
        int id = 0;
        try {
            String sql = "CALL GetNewPostId();";
            ResultSet a = stmt.executeQuery(sql);
            while (a.next()){
                id = a.getInt(1);
                break;
            }
            return id+1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    // 获取post的唯一辨识id


    public static int getNewBillId(Statement stmt, Connection conn){
        int id = 0;
        try {
            String sql = "CALL GetNewBillId();";
            ResultSet a = stmt.executeQuery(sql);
            while (a.next()){
                id = a.getInt(1);
                break;
            }
            return id+1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    // 获取post的唯一辨识id


    public static double getBalance(int uid, Statement stmt, Connection conn){
        double balance = 0;
        try {
            String sql = "CALL GetUserBalance("+uid+");";
            ResultSet a = stmt.executeQuery(sql);
            while (a.next()){
                balance = a.getDouble(1);
                break;
            }
            return balance;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    // 获取用户的余额


    public static int getPostAmount(int uid, Statement stmt, Connection conn){
        int amount = 0;
        try {
            String sql = "CALL GetUserPostAmount("+uid+");";
            ResultSet a = stmt.executeQuery(sql);
            while (a.next()){
                amount = a.getInt(1);
                break;
            }
            return amount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    // 获取用户的post数量


    public static int getBillAmount(int uid, Statement stmt, Connection conn){
        int amount = 0;
        try {
            String sql = "CALL GetBillCount("+uid+");";
            ResultSet a = stmt.executeQuery(sql);
            while (a.next()){
                amount = a.getInt(1);
                break;
            }
            return amount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }
    // 获取用户的账单数量


    public static int createUser(String username, String password, Statement stmt, Connection conn){
        try {

            int id = getNewUserId(stmt, conn);

            if(id == -1){
                return -2;
            }

            String sql = "insert into user_table(uid,username,password) values("+id+",'"+username+"','"+password+"');";

            int a = stmt.executeUpdate(sql);
            if(a == 1){
                return id;
            }else{
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    //  新建用户


    public static int createPost(String title, String content, int uid, Statement stmt, Connection conn){
        try {

            int id = getNewPostId(stmt, conn);

            if(id == -1){
                return -2;
            }

            String sql = "insert into posts(tid,title,content,uid) values("+id+",'"+title+"','"+content+"','"+uid+"');";

            int a = stmt.executeUpdate(sql);
            if(a == 1){
                return 1;
            }else{
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    //  新建Post


    public static int createBill(String title, String content, int uid, double amount, Statement stmt, Connection conn){
        try {

            int id = getNewBillId(stmt, conn);

            if(id == -1){
                return -2;
            }

            String sql = "insert into bills(bid,title,content,uid,amount) values("+id+",'"+title+"','"+content+"','"+uid+"','"+amount+"');";

            int a = stmt.executeUpdate(sql);
            if(a == 1){
                return 1;
            }else{
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    //  新建Bill


    public static int deletePost(int tid, Statement stmt, Connection conn){
        try {


            String sql = "DELETE FROM posts WHERE tid = "+tid+";";

            int a = stmt.executeUpdate(sql);
            if(a == 1){
                return 1;
            }else{
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    //  删除Post


    public Post[] fetchPost(int uid, Statement stmt, Connection conn){

        int amount = getPostAmount(uid,stmt,conn);

        Post[] post = new Post[amount];
        try {

            String sql = "SELECT * FROM posts WHERE uid = "+uid+";";

            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;

            while (rs.next()) {
                post[i] = new Post(rs.getInt("tid"), rs.getString("title"), rs.getString("content"),rs.getInt("uid"));
                i++;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
    //  获取某位用户的全部post


    public Bill[] fetchBill(int uid, Statement stmt, Connection conn){

        int amount = getBillAmount(uid,stmt,conn);

        Bill[] bill = new Bill[amount];
        try {

            String sql = "SELECT * FROM posts WHERE uid = "+uid+";";

            ResultSet rs = stmt.executeQuery(sql);
            int i = 0;

            while (rs.next()) {
                bill[i] = new Bill(rs.getInt("bid"), rs.getString("title"), rs.getString("content"),rs.getInt("uid"),rs.getDouble("amount"));
                i++;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }
    //  获取某位用户的全部账单


    public static boolean checkPassword(int uid, String passwword, Statement stmt, Connection conn){
        boolean state = false;

        try {

            String sql = "SELECT password FROM user_table WHERE uid = "+uid+";";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                if(Objects.equals(rs.getString("password"), passwword)){
                    state = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return state;
    }
    //  用于核验用户名与密码是否匹配


    public User getUserInfo(int uid, Statement stmt, Connection conn) {
        User user = null;

        try {

            String sql = "SELECT * FROM userinfo WHERE uid = " + uid + ";";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                user = new User(rs.getInt("uid"), rs.getString("username"),
                        rs.getString("shows"), rs.getInt("title_amount"), rs.getDouble("balance"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    //  用于获取用户信息


    public static void updatePost(int tid, String title, String content, Statement stmt, Connection conn){

        try {

            String sql = "UPDATE posts SET title = '"+title+"',content = '"+content+"' WHERE tid = '"+tid+"'";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //  修改Post


    public static void updateBill(int bid, String title, String content, Statement stmt, Connection conn){

        try {

            String sql = "UPDATE bills SET title = '"+title+"',content = '"+content+"' WHERE bid = '"+bid+"'";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //  修改bill
}
