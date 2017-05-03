package com.example.nguyenthanhthai.dictionaryandroid.model;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class Language {
    String languageId;
    String languageName;
    String description;

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Language(String languageId, String languageName, String description) {

        this.languageId = languageId;
        this.languageName = languageName;
        this.description = description;
    }
}
