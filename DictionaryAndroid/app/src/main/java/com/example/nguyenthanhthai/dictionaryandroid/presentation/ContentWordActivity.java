package com.example.nguyenthanhthai.dictionaryandroid.presentation;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.adapter.ContentWordAdapter;
import com.example.nguyenthanhthai.dictionaryandroid.domain.WordFavorite;
import com.example.nguyenthanhthai.dictionaryandroid.domain.WordMearning;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;

import java.util.Locale;

/**
 * Created by NguyenThanhThai on 3/27/2017.
 */

public class ContentWordActivity extends AppCompatActivity{
    ActionBar actionBar;
    Word word;

    String userNameLogin, password;
    TextToSpeech textToSpeech;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_word);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        userNameLogin=sharedPreferences.getString("UserName", "sa");
        password=sharedPreferences.getString("Password", "123");

        getActionMyBar();
        addControls();
        addEvents();
    }


    TextView wordText,wordPronounce;
    ImageView imageFavorite,imageSpeck;
    private void addControls() {
        wordText= (TextView) findViewById(R.id.wordText);
        wordPronounce= (TextView) findViewById(R.id.wordPronounce);
        imageFavorite= (ImageView) findViewById(R.id.imageFavorite);
        imageSpeck= (ImageView) findViewById(R.id.imageSpeek);
    }

    private void addEvents() {
        imageFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userNameLogin.equals("sa")){
                    Toast.makeText(ContentWordActivity.this, "You are not login!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (word.getFavoriteWord(userNameLogin,password)){
                    imageFavorite.setImageResource(R.drawable.unfavorite);
                    word.getRemoveWordFavorite(userNameLogin,password);
                }
                else {
                    imageFavorite.setImageResource(R.drawable.favorite);
                    word.getInsertWordFavorite(userNameLogin,password);
                }
            }
        });


        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        imageSpeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textToSpeech.speak(word.getWordText(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                new SearchWordActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getActionMyBar() {
        // get the action bar
        actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();


       handleIntent(getIntent());
    }

    //
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        word= WordMearning.getWord(intent.getIntExtra("WordId",0));
    }

    @Override
    protected void onStart() {
        super.onStart();
        userNameLogin=sharedPreferences.getString("UserName", "sa");
        password=sharedPreferences.getString("Password", "123");

        wordText.setText(word.getWordText());
        wordPronounce.setText(word.getPronounce());
        if (word.getFavoriteWord(userNameLogin,password)){
            imageFavorite.setImageResource(R.drawable.favorite);
        }
        else {
            imageFavorite.setImageResource(R.drawable.unfavorite);
        }

        ContentWordAdapter adapter = new ContentWordAdapter(word);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerMearning);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }
}
