package com.example.nguyenthanhthai.dictionaryandroid.model;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

public class WordType {
    int typeId;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    String typeName;

    public WordType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
