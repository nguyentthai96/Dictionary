package com.example.nguyenthanhthai.dictionaryandroid.domain;

import android.util.Log;

import com.example.nguyenthanhthai.dictionaryandroid.dao.DataAccess;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NguyenThanhThai on 3/28/2017.
 */

public class User {
    static DataAccess da;

    public static boolean getCheckIsLoginUserPass(String userName, String password) {
        String query = String.format("select  dbo.checkIsLoginUserPass('%s', '%s') as Exist",userName,password);
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);
//        ResultSet rs = da.excuteQueryLogin(query,userName,password);

        try {
            while (rs.next()) {
                int exist = rs.getInt("Exist");
                if (exist == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
            return false;
        }
        return false;
    }


}
