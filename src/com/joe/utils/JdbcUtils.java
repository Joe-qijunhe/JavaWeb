package com.joe.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();

    static {
        try {
            Properties properties = new Properties();
            InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(is);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection conn = conns.get();
        if (conn == null) {
            try {
                //从数据库连接池获取连接
                conn = dataSource.getConnection();
                //保存到ThreadLoacl对象中，供后面的jdbc操作使用
                conns.set(conn);
                //设置为手动管理事务
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    //提交事务并关闭连接
    public static void commitAndClose() {
        Connection conn = conns.get();
        //之前使用过连接，操作过数据库
        if (conn != null) {
            try {
                conn.commit(); //提交事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close(); //关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作 （因为Tomcat服务器底层实现了线程池）
        conns.remove();
    }

    //回滚并关闭连接
    public static void rollbackAndClose() {
        Connection conn = conns.get();
        //之前使用过连接，操作过数据库
        if (conn != null) {
            try {
                conn.rollback(); //回滚事务
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.close(); //关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //一定要执行remove操作 （因为Tomcat服务器底层实现了线程池）
        conns.remove();
    }

//    public static void close(Connection conn) {
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
