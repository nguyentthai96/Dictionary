package com.example.nguyenthanhthai.dictionaryandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.model.Example;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;
import com.example.nguyenthanhthai.dictionaryandroid.model.WordType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NguyenThanhThai on 3/27/2017.
 */

public class ContentWordAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int WORD_TYPE = 0;
    public static final int WORD_MEARING_TYPE = 1;
    public static final int EXAMPLE_TYPE = 2;

    class ModelCustomView {
        String wordType;
        String mearningContent;
        String mearningExample;
        String mearningExampleDescript;
        int type;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getWordType() {
            return wordType;
        }

        public void setWordType(String wordType) {
            this.wordType = wordType;
        }

        public String getMearningContent() {
            return mearningContent;
        }

        public void setMearningContent(String mearningContent) {
            this.mearningContent = mearningContent;
        }

        public String getMearningExample() {
            return mearningExample;
        }

        public void setMearningExample(String mearningExample) {
            this.mearningExample = mearningExample;
        }

        public String getMearningExampleDescript() {
            return mearningExampleDescript;
        }

        public void setMearningExampleDescript(String mearningExampleDescript) {
            this.mearningExampleDescript = mearningExampleDescript;
        }

        public ModelCustomView() {
        }

        public void setDataWordType(String wordType, int type) {
            this.wordType = wordType;
            this.type = type;
        }

        public void setDataMearning(String mearningContent, int type) {
            this.mearningContent = mearningContent;
            this.type = type;
        }

        public void setDataExample(String mearningExample, String mearningExampleDescript, int type) {
            this.mearningExample = mearningExample;
            this.mearningExampleDescript = mearningExampleDescript;
            this.type = type;
        }
    }

    Word word;
    List<ModelCustomView> listDatas;

    public ContentWordAdapter(Word word) {
        this.word = word;
        listDatas = loadData();
    }

    private List<ModelCustomView> loadData() {
        List<ModelCustomView> resualt = new ArrayList<>();

        List<WordType> wordTypes = word.getListMearningWordType();
        for (WordType type : wordTypes) {
            ModelCustomView wordTypeData = new ModelCustomView();
            wordTypeData.setDataWordType(type.getTypeName(), WORD_TYPE);
            resualt.add(wordTypeData);

            List<Mearning> mearnings = word.getListMearning(type);
            for (Mearning mearning : mearnings) {
                ModelCustomView mearningData = new ModelCustomView();
                mearningData.setDataMearning(mearning.getMearningText(), WORD_MEARING_TYPE);
                resualt.add(mearningData);

                if (mearning.getExamples() != null) {
                    for (Example example : mearning.getExamples()) {
                        ModelCustomView exampleData = new ModelCustomView();
                        exampleData.setDataExample(example.getExampleText(), example.getMearningExample(), EXAMPLE_TYPE);
                        resualt.add(exampleData);
                    }
                }
            }
        }

        return resualt;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case WORD_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_typeword, parent, false);
                return new WordTypeViewHolder(view);
            case WORD_MEARING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mearning, parent, false);
                return new MearningViewHolder(view);
            case EXAMPLE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mearning_example, parent, false);
                return new ExampleMearningViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ModelCustomView object = listDatas.get(position);
        if (object != null) {
            switch (object.getType()) {
                case WORD_TYPE:
                    ((WordTypeViewHolder) holder).mWordType.setText(object.getWordType());
                    break;
                case WORD_MEARING_TYPE:
                    ((MearningViewHolder) holder).mMearningContent.setText(object.getMearningContent());
                    break;
                case EXAMPLE_TYPE:
                    ((ExampleMearningViewHolder) holder).mMearningExample.setText(object.getMearningExample());
                    ((ExampleMearningViewHolder) holder).mMearningExampleDescript.setText(object.getMearningExampleDescript());
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (listDatas == null)
            return 0;
        return listDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (word != null) {
            if (listDatas.get(position) != null) {
                return listDatas.get(position).getType();
            }
        }
        return 0;
    }



    public static class WordTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView mWordType;

        public WordTypeViewHolder(View itemView) {
            super(itemView);
            mWordType = (TextView) itemView.findViewById(R.id.wordType);
        }
    }

    public static class MearningViewHolder extends RecyclerView.ViewHolder {
        private TextView mMearningContent;

        public MearningViewHolder(View itemView) {
            super(itemView);
            mMearningContent = (TextView) itemView.findViewById(R.id.mearningContentShow);
        }
    }

    public static class ExampleMearningViewHolder extends RecyclerView.ViewHolder {
        private TextView mMearningExample;
        private TextView mMearningExampleDescript;

        public ExampleMearningViewHolder(View itemView) {
            super(itemView);
            mMearningExample = (TextView) itemView.findViewById(R.id.mearningExample);
            mMearningExampleDescript = (TextView) itemView.findViewById(R.id.mearningExampleDescript);
        }
    }
}
