package com.example.nguyenthanhthai.dictionaryandroid.domain;

import android.util.Log;

import com.example.nguyenthanhthai.dictionaryandroid.dao.DataAccess;
import com.example.nguyenthanhthai.dictionaryandroid.model.Example;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;
import com.example.nguyenthanhthai.dictionaryandroid.model.WordType;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class WordMearning {
    static DataAccess da;

    public static Word getWord(int wordId) {
        String query = "exec spWordWithId " + wordId;
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);
        Word word=null;
        try {
            while (rs.next()) {
                int WordId = rs.getInt("WordId");
                String WordText = rs.getString("WordText");
                String LanguageId = rs.getString("LanguageId");
                String Pronounce = rs.getString("Pronounce");
                word=new Word(WordId,WordText,Pronounce);
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
        }
        return word;
    }

    /**
     * Get list word when search with wordtext
     */
    public Collection<Word> getListWordMearning(String wordText) {
        if (wordText == null) {
            return null;
        }
        String query = "exec spWordAndMeaning N'" + wordText + "'";
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);
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

    public static List<WordType> getListMearningWordType(Word word) {
        if (word == null) {
            return null;
        }
        String query = "exec spMeaningWordTypeList " + word.getWordId();
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);
        List<WordType>  listWordType;
        listWordType =new ArrayList<WordType>();
        try {
            while (rs.next()) {
                int TypeId = rs.getInt("TypeId");
                String TypeName = rs.getString("TypeName");
                listWordType.add(new WordType(TypeId, TypeName));
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
        }
        return listWordType;
    }

    public static List<Mearning> getListMearning(Word word, WordType type) {
        if (word == null) {
            return null;
        }

        String query = "exec spListMeaningWithType "+ word.getWordId()+ ", 'vi', "+type.getTypeId();
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);

        List<Mearning> mearnings=new ArrayList<>();

        try {
            while (rs.next()) {

                if (rs.getObject("ExampleId")!=null) {
                    int exampleGroupId=rs.getInt("ExampleId");
                    String MearningText = rs.getString("MearningText");
                    List<Example> examples =new ArrayList<>();
                    examples=getListExample(exampleGroupId);
                    mearnings.add(new Mearning(MearningText,examples));
                }else {
                    String MearningText = rs.getString("MearningText");
                    mearnings.add(new Mearning(MearningText,null));
                }
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
        }

        return  mearnings;
    }

    private static List<Example> getListExample(int exampleGroupId) {
        String query = "exec spListExample " + exampleGroupId;
        da = new DataAccess();
        ResultSet rs = da.excuteQuery(query);
        List<Example>  listExample;
        listExample =new ArrayList<Example>();
        try {
            while (rs.next()) {
                int ExampleId = rs.getInt("ExampleId");
                String ExampleText = rs.getString("ExampleText");
                String MearningExample = rs.getString("MearningExample");
                listExample.add(new Example(ExampleId,ExampleText,MearningExample));
            }
        } catch (Exception ex) {
            Log.d("\n\nEX", ex.getMessage());
        }
        return listExample;
    }
}