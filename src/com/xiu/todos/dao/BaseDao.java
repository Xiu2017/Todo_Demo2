package com.xiu.todos.dao;

import com.xiu.todos.util.PropertiesUtil;

import java.sql.*;
import java.util.List;

/**
 * 通用的Dao
 * @author xiu
 * @date 2019/1/8 14:12
 */
public class BaseDao{
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    static {
        String databaseDriver = PropertiesUtil.getPropertiesByKey("databaseDriver");
        try {
            Class.forName(databaseDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接
     */
    private static void connection() {
        if (connection == null) {
            String databaseUrl = PropertiesUtil.getPropertiesByKey("databaseUrl");
            String databaseUser = PropertiesUtil.getPropertiesByKey("databaseUser");
            String databasePassword = PropertiesUtil.getPropertiesByKey("databasePassword");
            try {
                connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     */
    public static void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }
            if (preparedStatement != null) {
                preparedStatement.close();
                preparedStatement = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     * @param sql 查询语句
     * @param params 参数
     * @return 查询结果
     */
    public static ResultSet executeQuery(String sql, List<Object> params) {
        connection();
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 更新
     * @param sql 更新语句
     * @param params 参数
     * @return 受影响的行数
     */
    public static int executeUpdate(String sql, List<Object> params) {
        connection();
        int result = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            if (params != null && params.size() > 0) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
}
