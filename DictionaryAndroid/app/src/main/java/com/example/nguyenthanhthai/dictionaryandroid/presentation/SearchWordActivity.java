package com.example.nguyenthanhthai.dictionaryandroid.presentation;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.adapter.WordMearningAdapter;
import com.example.nguyenthanhthai.dictionaryandroid.customview.CustomRVItemTouchListener;
import com.example.nguyenthanhthai.dictionaryandroid.customview.RecyclerViewItemClickListener;
import com.example.nguyenthanhthai.dictionaryandroid.domain.WordMearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Mearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchWordActivity extends AppCompatActivity {

    private List<Word> words = new ArrayList<>();
    private RecyclerView recyclerView;
    private WordMearningAdapter adapter;
    SearchView searchView;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        getActionBar_();
        addControls();
    }

    private void getActionBar_() {
        // get the action bar
        actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();


    }

    private void addControls() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new WordMearningAdapter(words);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL);
        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_divider));
        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(getBaseContext(),ContentWordActivity.class);
                intent.putExtra("WordId", words.get(position).getWordId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_actions, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        addEventQueryText();

        return super.onCreateOptionsMenu(menu);
    }

    private void addEventQueryText() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String textChange) {
                viewListWordSearch(textChange);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                viewListWordSearch(query);
                ////Hide keyboard
                // Check if no view has focus:
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    private void viewListWordSearch(String textWord) {
        if (textWord == "")
            return;
        LoadData loadDataThread = new LoadData();
        loadDataThread.execute(textWord);
    }

    public class LoadData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            words.clear();
        }

        @Override
        protected String doInBackground(String... params) {
            WordMearning wordmearning = new WordMearning();
            words.addAll(wordmearning.getListWordMearning(params[0]));
            return "run";
        }

        @Override
        protected void onPostExecute(String s) {
            Collections.sort(words, new Comparator<Word>() {
                @Override
                public int compare(Word o1, Word o2) {
                    return o1.getWordText().compareTo(o2.getWordText());
                }
            });
            try {
                adapter.notifyDataSetChanged();
            } catch (Exception ex) {
                Log.d("EXXXXXXXXXXXX", ex.getMessage());
            }
        }
    }
}