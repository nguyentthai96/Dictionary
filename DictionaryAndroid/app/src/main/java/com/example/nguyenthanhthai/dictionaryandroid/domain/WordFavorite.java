package com.example.nguyenthanhthai.dictionaryandroid.domain;

import android.util.Log;

import com.example.nguyenthanhthai.dictionaryandroid.dao.DataAccess;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;
import com.example.nguyenthanhthai.dictionaryandroid.model.WordType;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NguyenThanhThai on 3/28/2017.
 */

public class WordFavorite {
    static DataAccess da;


    public static boolean getCheckWordFavorite(int wordId, String userName, String password) {

        if (userName.equals("sa")){
            return false;
        }

        String query = "select count(WordId) as WordExist from WordFavorites where WordId=" + wordId+" and UserName='"+userName+"'";
        da = new DataAccess();
        ResultSet rs = da.excuteQueryLogin(query,userName,  password);

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
            return false;
        }
        return false;
    }

    public static void getInsertWordFavorite(int wordId, String userName, String password) {
        if (userName.equals("sa")){
            return;
        }
        Calendar c = Calendar.getInstance();
        DateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");//foramt date
        String date = df1.format(Calendar.getInstance().getTime());
        String query = "insert into WordFavorites(WordId, DateFavorites, UserName) values (" + wordId + ",'" + date+ "','" + userName + "')";
        da = new DataAccess();
        da.excuteNonQueryLogin(query,userName,  password);
    }

    public static void getRemoveWordFavorite(int wordId, String userName, String password) {
        String query = "delete from WordFavorites where WordId="+wordId+" and UserName='"+userName+"'";
        da = new DataAccess();
        da.excuteNonQueryLogin(query,userName,  password);
    }


    /**
     * Get list word book mark
     */
    public static Collection<Word> getListWordBookMark(String userName, String password){
        if (userName == null) {
            return null;
        }
        String query = "exec spWordListBookMark N'" + userName + "'";
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query,userName);
        Map<Integer, Word> mearningMapWordId;
        mearningMapWordId = new HashMap<Integer, Word>();
        try {
            while (rs.next()) {
                int wordId = rs.getInt("WordId");
                String wordTextGet = rs.getString("WordText");
                String pronounce = rs.getString("Pronounce");
                String mearningText = rs.getString("MearningText");
                Mearning mearning = new Mearning();
                mearning.setMearningText(mearningText);

                if (mearningMapWordId.containsKey(wordId)) {
                    mearningMapWordId.get(wordId).getMearnings().add(mearning);
                    continue;
                }

                Word word = new Word(wordId, wordTextGet, pronounce);
                word.setMearnings(new ArrayList<Mearning>());
                word.getMearnings().add(mearning);
                mearningMapWordId.put(wordId, word);
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
        }

        return mearningMapWordId.values();
    }

}
