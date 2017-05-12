package com.example.nguyenthanhthai.dictionaryandroid.adapter;

/**
 * Created by NguyenThanhThai on 3/18/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;
import com.example.nguyenthanhthai.dictionaryandroid.presentation.SelectWordActivity;

import java.util.List;

public class SelectWordAdapter extends RecyclerView.Adapter<SelectWordAdapter.MyViewHolder> {

    private List<SelectWordActivity.WordStringId> wordLists;


    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView word;

        public MyViewHolder(View view) {
            super(view);
            word = (TextView) view.findViewById(R.id.word);
        }

    }


    public SelectWordAdapter(List<SelectWordActivity.WordStringId> wordLists) {
        this.wordLists = wordLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_word_list_row, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SelectWordActivity.WordStringId word = wordLists.get(position);
        holder.word.setText(word.getWordText());
        holder.word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerRecycler.onClickItemRecyclerView(word);
            }
        });
    }
    SelectWordActivity.ListenerRecycler listenerRecycler;
    public void setOnClickItem(SelectWordActivity.ListenerRecycler listenerRecycler){
        this.listenerRecycler=listenerRecycler;
    }

    @Override
    public int getItemCount() {
        return wordLists.size();
    }
}

