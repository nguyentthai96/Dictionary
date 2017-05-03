package com.example.nguyenthanhthai.dictionaryandroid.model;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class Example {
    int exampleId;
    String exampleText;
    String mearningExample;

    public String getExampleText() {
        return exampleText;
    }

    public void setExampleText(String exampleText) {
        this.exampleText = exampleText;
    }

    public String getMearningExample() {
        return mearningExample;
    }

    public void setMearningExample(String mearningExample) {
        this.mearningExample = mearningExample;
    }

    public Example(int exampleId, String exampleText, String mearningExample) {
        this.exampleId = exampleId;
        this.exampleText = exampleText;
        this.mearningExample = mearningExample;

    }
}
