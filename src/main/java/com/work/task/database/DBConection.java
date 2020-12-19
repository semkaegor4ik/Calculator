package com.work.task.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.*;
import java.util.Properties;

public class DBConection {

    private static final Properties PROPERTIES = new Properties();
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static{
        try(FileInputStream fis = new FileInputStream("src/main/resources/db.properties")){
            PROPERTIES.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        URL = PROPERTIES.getProperty("datasource.url");
        USER = PROPERTIES.getProperty("datasource.username");
        PASSWORD = PROPERTIES.getProperty("datasource.password");
    }

    public static Connection connect() throws SQLException {
         return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
