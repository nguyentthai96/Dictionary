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

    public void excuteNonQuery(String strSqlCommand, String... params) {
        try {
            con = new ConnectionSql().connectString();
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
            con = new ConnectionSql().connectString();
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
