package main.project_1;

import java.sql.*;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @ProjectName: Test
 * @Description: java类作用描述
 * @Author: horse
 * @CreateDate: 2019/5/5 1:11
 * @UpdateUser: horse
 * @UpdateDate: 2019/5/5 1:11
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2019</p>
 */
public class DBUtils {
    /*
     * MySQL在linux服务器上面的数据库名字，
     * 以及和要操作的表的名字
     * */
    private static final String mydatabase="mydatabase";
    private static final String mytable_2="library_table";

    private static final String DBDRIVER = "com.mysql.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://112.74.35.126:3306/"+mydatabase
            +"?useUnicode=true&characterEncoding=UTF8";
    private static final String DBUSER = "root";
    private static final String DBPASSWORD = "Horse123+";


    /*
    * 获取 Connection
    * */
    public static Connection getConnection() {
        Connection conn=null;
        try {
            Class.forName(DBDRIVER);
            conn = DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /*
    * 释放资源
    * */
    public static void release(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 释放资源
     * */
    public static void release(Connection conn, PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*
    * 写数据库
    * */
    public static void func_1(System_m.Reader reader){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
                    System.out.println(reader.getDate());
            String sql = "insert into "+mytable_2
                    +"(num,name,xueyuan,major,grade,date,book_name,book_author,publishing_name)"
                    +"values"
                    +"("
                    +"\'"+reader.getNum()+"\'"+","
                    +"\'"+reader.getName()+"\'"+","
                    +"\'"+reader.getXueyuan()+"\'"+","
                    +"\'"+reader.getMajor()+"\'"+","
                    +"\'"+reader.getGrade()+"\'"+","
                    +"\'"+reader.getDate()+"\'"+","
                    +"\'"+reader.getBook().getBook_name()+"\'"+","
                    +"\'"+reader.getBook().getBook_author()+"\'"+","
                    +"\'"+reader.getBook().getPublishing_name()+"\'"
                    +");";
            System.out.println(sql);
/*            String sql = "insert into "+mytable_2
                    +"(num,name,xueyuan,major,grade,date,book_name,book_author,publishing_name)"
                    +"values"
                    +"("
                    +"\'"+reader.getNum()+"\'"+","
                    +reader.getName()+"\'"+","
                    +reader.getXueyuan()+"\'"+","
                    +reader.getMajor()+"\'"+","
                    +"1"+","
                    +"123"+","
                    +"123"+","
                    +"234"+","
                    +"你好"
                    +")";*/
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7.释放资源
            release(conn, pstmt);
        }
    }


    /*
    * 查询全部
    * */
    public static void func_2(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        /*
        * ResultSet,结果集，对应数据库的存储形式，二维的数组？？？！！！
        * */
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "select * from "+mytable_2;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
               //
                System.out.println(rs.getInt(1)+rs.getString(2));
                System.out.println(rs.getInt("user_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7.释放资源
            release(conn, pstmt, rs);
        }
    }
}

