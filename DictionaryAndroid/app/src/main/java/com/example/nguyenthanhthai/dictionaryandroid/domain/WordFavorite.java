package com.example.nguyenthanhthai.dictionaryandroid.domain;

import android.util.Log;

import com.example.nguyenthanhthai.dictionaryandroid.dao.DataAccess;
import com.example.nguyenthanhthai.dictionaryandroid.model.WordType;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by NguyenThanhThai on 3/28/2017.
 */

public class WordFavorite {
    static DataAccess da;

    public static boolean getCheckWordFavorite(int wordId) {
        String query = "select count(WordId) as WordExist from WordFavorites where WordId=" + wordId;
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);

        try {
            while (rs.next()) {
                int exist = rs.getInt("WordExist");
                if (exist == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
        }
        return true;
    }

    public static void getInsertWordFavorite(int wordId) {
        Calendar c = Calendar.getInstance();
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");//foramt date
        String date = df1.format(Calendar.getInstance().getTime());
        String query = "insert into WordFavorites(WordId, DateFavorites) values (" + wordId + ",'" + date + "')";
        da = new DataAccess();
        da.excuteNonQuery(query);
    }

    public static void getRemoveWordFavorite(int wordId) {
        String query = "delete from WordFavorites where WordId="+wordId;
        da = new DataAccess();
        da.excuteNonQuery(query);
    }
}
