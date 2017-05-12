package com.example.nguyenthanhthai.dictionaryandroid.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class DataAccess {
    Connection con;
    static String ip = "192.168.43.136:1433";
    static String un = "sa";
    static String password = "123";


    public void excuteNonQuery(String strSqlCommand, String... params) {
        try {
            con = new ConnectionSql().connectString(ip,un,password);
            if (con == null) {
                Log.d("\nDebug::::::::\n", "Error in connection with SQL server");
            } else {
                PreparedStatement preparedStatement = con.prepareStatement(strSqlCommand);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            Log.d("\nDebug::::::::\n", ex.getMessage());
        }
    }

    public ResultSet excuteQuery(String sqlQueryData, String... params) {
        ResultSet rs =null;

        try {
            con = new ConnectionSql().connectString(ip,un,password);
            if (con == null) {
                Log.d("\nDebug::::::::\n", "Error in connection with SQL server");
            } else {
                PreparedStatement ps = con.prepareStatement(sqlQueryData);
                rs = ps.executeQuery();
            }
        } catch (Exception ex) {
            Log.d("\nDebug::::::::\n", ex.getMessage());
        }
        return rs;
    }


    public void excuteNonQueryLogin(String strSqlCommand, String userName, String password, String... params) {
        try {
            con = new ConnectionSql().connectString(ip,userName,password);
            if (con == null) {
                Log.d("\nDebug::::::::\n", "Error in connection with SQL server");
            } else {
                PreparedStatement preparedStatement = con.prepareStatement(strSqlCommand);
                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            Log.d("\nDebug::::::::\n", ex.getMessage());
        }
    }

    public ResultSet excuteQueryLogin(String sqlQueryData, String userName, String password, String... params) {
        ResultSet rs =null;

        try {
            con = new ConnectionSql().connectString(ip,userName,password);
            if (con == null) {
                Log.d("\nDebug::::::::\n", "Error in connection with SQL server");
            } else {
                PreparedStatement ps = con.prepareStatement(sqlQueryData);
                rs = ps.executeQuery();
            }
        } catch (Exception ex) {
            Log.d("\nDebug::::::::\n", ex.getMessage());
        }
        return rs;
    }
}
