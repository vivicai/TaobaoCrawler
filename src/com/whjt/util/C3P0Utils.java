package com.whjt.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {
    public static final String driverclass = "com.mysql.jdbc.Driver";

    //public static final String url = "jdbc:mysql://192.168.232.99:9083/stevenkuang?useUnicode=true&characterEncoding=utf-8";
    public static final String url = "jdbc:mysql://60.212.191.147:9083/stevenkuang?useUnicode=true&characterEncoding=utf-8";
    public static final String username = "root";
    public static final String password = "abcd.1234";

    // public static final String driverclass="com.ingres.jdbc.IngresDriver";
    // public static final String url="jdbc:ingres://112.65.211.69:vw7/test";
    // public static final String username="Datavector";
    // public static final String password="actian";

    private static C3P0Utils dbcputils = null;
    private ComboPooledDataSource cpds = null;

    private C3P0Utils() {
        if (cpds == null) {
            cpds = new ComboPooledDataSource();
        }
        cpds.setUser(username);
        cpds.setPassword(password);
        cpds.setJdbcUrl(url);
        try {
            cpds.setDriverClass(driverclass);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setInitialPoolSize(100);
        cpds.setMaxIdleTime(20);
        cpds.setMaxPoolSize(100);
        cpds.setMinPoolSize(10);
    }

    public C3P0Utils(String url, String username, String password) {

        cpds = new ComboPooledDataSource();
        cpds.setUser(username);
        cpds.setPassword(password);
        cpds.setJdbcUrl(url);
        try {
            cpds.setDriverClass(driverclass);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setInitialPoolSize(100);
        cpds.setMaxIdleTime(20);
        cpds.setMaxPoolSize(100);
        cpds.setMinPoolSize(10);
    }

    public synchronized static C3P0Utils getInstance() {
        if (dbcputils == null)
            dbcputils = new C3P0Utils();
        return dbcputils;
    }

    public synchronized static C3P0Utils getInstance(String url,
            String username, String password) {
        return new C3P0Utils(url, username, password);
    }

    public Connection getConnection() {
        Connection con = null;
        try {
            con = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        long begin = System.currentTimeMillis();
        con = C3P0Utils.getInstance().getConnection();
        String sql = "select * from taxi_taxiinfo";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("sim"));
        }
        rs.close();
        con.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin) + "ms");
    }
}
