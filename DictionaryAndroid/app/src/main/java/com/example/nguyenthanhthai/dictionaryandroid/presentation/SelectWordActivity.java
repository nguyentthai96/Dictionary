package com.example.nguyenthanhthai.dictionaryandroid.presentation;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.adapter.SelectWordAdapter;
import com.example.nguyenthanhthai.dictionaryandroid.domain.WordMearning;
import com.example.nguyenthanhthai.dictionaryandroid.ocr.CaptureActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SelectWordActivity extends AppCompatActivity {

    Intent intent;
    private RecyclerView recyclerView;
    ArrayList<WordStringId> wordStringIds;
    SelectWordAdapter adapter;
    String textTranslate;
    ActionBar actionBar;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_word);

        getActionBar_();
        intent = getIntent();

        textTranslate = intent.getStringExtra("OCRResultString");
        Toast.makeText(this, textTranslate, Toast.LENGTH_SHORT).show();
        Log.d("SelectWord",textTranslate);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL);
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_divider));

        wordStringIds=new ArrayList<>();
         adapter=new SelectWordAdapter(wordStringIds);
        recyclerView.setAdapter(adapter);

        reverstString();

        adapter.setOnClickItem(new ListenerRecycler() {
            @Override
            public void onClickItemRecyclerView(WordStringId wordStringId) {
                Intent intent=new Intent(getBaseContext(),ContentWordActivity.class);
                intent.putExtra("WordId", wordStringId.getWordId());
                startActivity(intent);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectWordActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getActionBar_() {
        // get the action bar
        actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
    }
    private void reverstString() {
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(textTranslate.trim().split(" ")));

        wordStringIds.clear();
        for (String item : strings) {
            int wordId= WordMearning.getWordId(item);
            if (wordId!=-1){
                wordStringIds.add(new WordStringId(wordId,item.toLowerCase()));
            }
        }

        if (wordStringIds.isEmpty()){
            Toast.makeText(this, "Cannot find word in database, You try!!", Toast.LENGTH_LONG).show();
            finish();
        }else{
            if(wordStringIds.size()==1){
                Intent intent=new Intent(getBaseContext(),ContentWordActivity.class);
                intent.putExtra("WordId", wordStringIds.get(0).getWordId());
                startActivity(intent);
                finish();
            }
        }

         adapter.notifyDataSetChanged();
    }


    public class WordStringId {
        int wordId;
        String wordText;

        public WordStringId(int wordId, String wordText) {
            this.wordId = wordId;
            this.wordText = wordText;
        }

        public int getWordId() {
            return wordId;
        }

        public String getWordText() {
            return wordText;
        }
    }

    public interface ListenerRecycler{
        void onClickItemRecyclerView(WordStringId wordStringId);
    }
}
