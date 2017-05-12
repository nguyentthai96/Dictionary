package com.example.nguyenthanhthai.dictionaryandroid.dao;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by NguyenThanhThai on 3/6/2017.
 */

public class ConnectionSql {

    static String classs = "net.sourceforge.jtds.jdbc.Driver";
    static String db = "DictionaryDb";

    @SuppressLint("NewApi")
    public static Connection connectString( String ip, String un, String password) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName(classs);
            final String  driver="jdbc:jtds:sqlserver://";
            ConnURL = driver+ ip + ";" + "databaseName=" + db + ";user=" + un + ";password=" + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("\nERRO SQLException", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("\nERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("\nERRO", e.getMessage());
        }
        return conn;
    }
}