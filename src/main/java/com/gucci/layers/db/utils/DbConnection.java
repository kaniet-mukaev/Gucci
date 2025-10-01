package com.gucci.layers.db.utils;

import com.gucci.config.ConfigurationManager;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class DbConnection {
    private static Connection connection;
    private static Statement statement; //нужен для того чтобы делать не параметризированные запросы

    private static PGSimpleDataSource getBaseDataSource(String database) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource() {{
            setServerName(ConfigurationManager.getAppConfig().server());
            setPortNumber(ConfigurationManager.getAppConfig().port());
            setUser(ConfigurationManager.getAppConfig().user());
            setPassword(ConfigurationManager.getAppConfig().sqlpassword());
            setDatabaseName(database);
        }};
        return dataSource;
    }

    public static void openConnection(String database) {
        if (connection == null) {
            try {
                connection = getBaseDataSource(database).getConnection();
                statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //дз реализовать try with resources два метода
    private static void closeConnection(String database) {
        try {
            if (statement != null) {
                statement.close();
                statement = null;
            }
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet query(String query, Object... params) throws SQLException {
        if (params.length == 0) {
            return statement.executeQuery(query);
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            return preparedStatement.executeQuery();
        }
    }
}
