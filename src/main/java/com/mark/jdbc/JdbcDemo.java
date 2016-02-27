package com.mark.jdbc;

import java.sql.*;

/**
 * Author: Mark
 * Date  : 16/2/27.
 */
public class JdbcDemo {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "root");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (conn != null) {
            try {
                DatabaseMetaData metaData = conn.getMetaData();
                System.out.println(metaData.getDatabaseProductName());
                System.out.println(metaData.getDatabaseProductVersion());
                System.out.println(metaData.getDriverName());
                System.out.println(metaData.getDriverVersion());
                System.out.println(metaData.getDefaultTransactionIsolation());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String sql = "select * from user where id = 1";
            ResultSet rs = null;
            Statement statement = null;
            try {
                statement = conn.createStatement();
                rs = statement.executeQuery(sql);
                if (rs != null) {
                    while (rs.next()) {
                        long id = rs.getLong("id");
                        String nickname = rs.getString("nickname");
                        System.out.println(id + " : " + nickname);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            statement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

    }


}
