package com.example.nguyenthanhthai.dictionaryandroid.model;

import java.util.List;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class Mearning {
    String mearningText;
    Word word;
    Language language;
    List<Example> examples;

    public Mearning(String mearningText, List<Example> examples) {
        this.mearningText = mearningText;
        this.examples = examples;
    }

    public List<Example> getExamples() {

        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public String getMearningText() {
        return mearningText;
    }

    public void setMearningText(String mearningText) {
        this.mearningText = mearningText;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Mearning(String mearningText) {

        this.mearningText = mearningText;
    }
    public Mearning() {}
}
