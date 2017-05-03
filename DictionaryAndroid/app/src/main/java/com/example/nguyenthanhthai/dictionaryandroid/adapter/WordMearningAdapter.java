package com.example.nguyenthanhthai.dictionaryandroid.adapter;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WordMearningAdapter extends RecyclerView.Adapter<WordMearningAdapter.MyViewHolder> {

    private List<Word> wordLists;


    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView word, mearning;

        public MyViewHolder(View view) {
            super(view);
            word = (TextView) view.findViewById(R.id.word);
            mearning = (TextView) view.findViewById(R.id.mearning);
        }

    }


    public WordMearningAdapter(List<Word> wordLists) {
        this.wordLists = wordLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.word_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Word word = wordLists.get(position);
        holder.word.setText(word.getWordText());
        String allMearningOfWord=allMearning(word);
        holder.mearning.setText(allMearningOfWord);
    }
    private String allMearning(Word word) {
        String result="";
        List<Mearning> mearings= word.getMearnings();
        if (mearings==null)
            return  "";
        for (Mearning me: mearings) {
            result+=me.getMearningText()+"; ";
        }
        return  result.substring(0,result.length()-1);
    }

    @Override
    public int getItemCount() {
        return wordLists.size();
    }
}

