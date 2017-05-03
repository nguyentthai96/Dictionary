package com.example.nguyenthanhthai.dictionaryandroid.model;

import com.example.nguyenthanhthai.dictionaryandroid.domain.WordFavorite;
import com.example.nguyenthanhthai.dictionaryandroid.domain.WordMearning;

import java.io.Serializable;
import java.util.List;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class Word implements Serializable{
    int wordId;

    public Word() {
    }

    public Word(int wordId, String wordText, String pronounce) {
        this.wordId = wordId;
        this.wordText = wordText;
        this.pronounce = pronounce;
    }

    public Word(int wordId, String wordText, String pronounce, Language language) {
        this.wordId = wordId;
        this.wordText = wordText;
        this.pronounce = pronounce;
        this.language = language;
    }

    String wordText;
    String pronounce;

    Language language;
    List<Mearning> mearnings;

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Mearning> getMearnings() {
        return mearnings;
    }

    public void setMearnings(List<Mearning> mearnings) {
        this.mearnings = mearnings;
    }

    /*
    * get list all word type of  many mearning
     */
    public List<WordType> getListMearningWordType(){
        return WordMearning.getListMearningWordType(this);
    }
    /*
    * get list all word type of  many mearning
     */
    public List<Mearning> getListMearning(WordType type){
        return WordMearning.getListMearning(this, type);
    }

    public boolean getFavoriteWord(){
        return WordFavorite.getCheckWordFavorite(this.wordId);
    }

    public void getInsertWordFavorite() {
        WordFavorite.getInsertWordFavorite(this.wordId);
    }
    public void getRemoveWordFavorite() {
        WordFavorite.getRemoveWordFavorite(this.wordId);
    }
}
