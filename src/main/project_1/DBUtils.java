package main.project_1;

import main.project_1.inter.Back;

import java.sql.*;
import java.util.ArrayList;
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
    * 查询读者的借阅信息
    * */
    public static void func_2(String num, Back back){
        Connection conn = null;
        PreparedStatement pstmt = null;
        /*
        * ResultSet,结果集，对应数据库的存储形式，二维的数组？？？！！！
        * */
        ResultSet rs = null;
        List<System_m.Reader>lists=new ArrayList<>();
        try {
            conn = getConnection();
            String sql = "select num,name,xueyuan,major,grade,date,book_name,book_author,publishing_name" +
                    " from "+mytable_2+" " +
                    "where num="+"\'"+num+"\'"+";";
            System.out.println(sql);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System_m.Reader reader=new System_m.Reader();
                System.out.println(rs.toString()+"");
                reader.setNum(rs.getString(1));
                reader.setName(rs.getString(2));
                reader.setXueyuan(rs.getString(3));
                reader.setMajor(rs.getString(4));
                reader.setGrade(rs.getString(5));
                reader.setDate(rs.getString(6));
                System_m.Book book=new System_m.Book();
                book.setBook_name(rs.getString(7));
                book.setBook_author(rs.getString(8));
                book.setPublishing_name(rs.getString(9));
                reader.setBook(book);
                lists.add(reader);
            }
            back.success(lists);
        } catch (Exception e) {
            back.error();
            e.printStackTrace();
        } finally {
            // 7.释放资源
            release(conn, pstmt, rs);
        }
    }

    /*
    * 读者归还课本后，对信息进行删除
    * */
    public static void func_3(System_m.Reader reader){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql="delete from "+mytable_2+" where num = "+"\'"+reader.getNum()+"\'"
                    +"and book_name = "+'\''+reader.getBook().getBook_name()+"\'"
                    +"and date = "+"\'"+reader.getDate()+"\'"+";";
            System.out.println(sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 7.释放资源
            release(conn, pstmt);
        }
    }

}

