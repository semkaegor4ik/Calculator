package com.work.task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class History {

    public void createTable() {
        try (Connection con = DBConection.connect()) {
            Statement statement = con.createStatement();
            String sqlCommand = "CREATE TABLE IF NOT EXISTS history (formula VARCHAR, createDate timestamp)";
            statement.executeUpdate(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<String> getHistory(){

        List<String> history = new ArrayList<>();
        try (Connection con = DBConection.connect()) {
            Statement statement = con.createStatement();
            String sqlCommand = "WITH t AS (" +
                    " SELECT * FROM history ORDER BY createDate DESC LIMIT 20)" +
                    " SELECT * FROM t ORDER BY createDate ASC";
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                history.add(resultSet.getString("formula"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return history;

    }

    public void addFormula(String formula){
        try (Connection con = DBConection.connect()) {
            Statement statement = con.createStatement();
            String sqlCommand = "INSERT INTO history " +
                    "VALUES ('"+formula+"', '"+LocalDateTime.now().withNano(0).withSecond(0)+"')";
            statement.executeUpdate(sqlCommand);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }




}
