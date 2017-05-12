package com.example.nguyenthanhthai.dictionaryandroid.presentation;

import android.accessibilityservice.AccessibilityService;
import android.app.SearchManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenthanhthai.dictionaryandroid.R;
import com.example.nguyenthanhthai.dictionaryandroid.adapter.WordMearningAdapter;
import com.example.nguyenthanhthai.dictionaryandroid.customview.CustomRVItemTouchListener;
import com.example.nguyenthanhthai.dictionaryandroid.customview.RecyclerViewItemClickListener;
import com.example.nguyenthanhthai.dictionaryandroid.domain.User;
import com.example.nguyenthanhthai.dictionaryandroid.domain.WordFavorite;
import com.example.nguyenthanhthai.dictionaryandroid.model.Word;
import com.example.nguyenthanhthai.dictionaryandroid.ocr.CaptureActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionBar.OnNavigationListener {

    // action bar
    private ActionBar actionBar;
    private SearchView searchView;
    private FloatingActionButton fab;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String userName, password;
    LinearLayout listWordBookmark;
    LinearLayout loginAccount;

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.login_account);
        listWordBookmark = (LinearLayout) findViewById(R.id.list_word);
        loginAccount = (LinearLayout) findViewById(R.id.login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        setControlRecyclerView();

        setActionBar_();
    }

    private List<Word> words = new ArrayList<>();
    private RecyclerView recyclerView;
    private WordMearningAdapter adapter;

    private void setControlRecyclerView() {
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
                Intent intent = new Intent(getBaseContext(), ContentWordActivity.class);
                intent.putExtra("WordId", words.get(position).getWordId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    MenuItem loginTitleMenuItem;
    MenuItem logoutMenuItem;


    private void setActionBar_() {
        actionBar = getSupportActionBar();

        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.show();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_actions, menu);


        loginTitleMenuItem = menu.findItem(R.id.acount_name);
        logoutMenuItem = menu.findItem(R.id.action_logout);

        loginTitleMenuItem.setVisible(false);
        logoutMenuItem.setVisible(false);

        if (sharedPreferences.contains("UserName")) {
            userName = sharedPreferences.getString("UserName", "sa");
            password = sharedPreferences.getString("Password", "123");
            ((TextView) findViewById(R.id.userName)).setText(userName);
            ((TextView) findViewById(R.id.password)).setText(password);

            loginTitleMenuItem.setVisible(true);
            logoutMenuItem.setVisible(true);
            loginTitleMenuItem.setTitle(userName);

            login.callOnClick();
            loadDataRecyclerView();
        } else {
            listWordBookmark.setVisibility(View.GONE);
            loginAccount.setVisibility(View.VISIBLE);
            words.clear();
        }

        // Associate searchable configuration with the SearchView
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //searchView.onActionViewExpanded();
        //searchView.clearFocus();

//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchWordActivity.class)));

//        TODO call listenOnClickSearchView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchView.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View v) {
                    Log.d("TTT", "setOnContextClickListener");
                    return false;
                }
            });
        }

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("TTT", "setOnCloseListener");
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TTT", "setOnSearchClickListener");
                searchView.setIconifiedByDefault(true);
                searchView.setIconified(true);
                searchView.clearFocus();
                Intent intent = new Intent(searchView.getContext(), SearchWordActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TTT", "setOnClickListener");
            }
        });

        searchView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                Log.d("TTT", "setOnHoverListener");
                return false;
            }
        });
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d("TTT", "2");
                searchView.setIconifiedByDefault(true);
                searchView.setIconified(true);
                searchView.clearFocus();
                Intent intent = new Intent(searchView.getContext(), SearchWordActivity.class);
                startActivity(intent);
            }
        });

        searchView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                Log.d("TTT", "onApplyWindowInsets");
                return null;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                Log.d("TTT", "onSuggestionSelect");
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Log.d("TTT", "onSuggestionSelect");
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                Log.d("TTT", "onOptionsItemSelected action_search");
                return true;
            case R.id.action_logout:
                // location found
                logOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logOut() {
        editor.remove("UserName");
        editor.commit();
        loginTitleMenuItem.setVisible(false);
        logoutMenuItem.setVisible(false);

        listWordBookmark.setVisibility(View.GONE);
        loginAccount.setVisibility(View.VISIBLE);
        words.clear();
    }


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }

    public void loginOnClick(android.view.View view) {

        userName = ((TextView) findViewById(R.id.userName)).getText().toString();
        password = ((TextView) findViewById(R.id.password)).getText().toString();

        if (userName.length() < 1 || password.length() < 1) {
            Toast.makeText(this, "Wrong login, fill empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (User.getCheckIsLoginUserPass(userName, password)) {
            editor.putString("UserName", userName);
            editor.putString("Password", password);
            editor.commit();

            loginTitleMenuItem.setVisible(true);
            logoutMenuItem.setVisible(true);
            loginTitleMenuItem.setTitle(userName);

            loadDataRecyclerView();
        } else {
            Toast.makeText(this, "Login failed! Login again", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.contains("UserName")) {
            userName = sharedPreferences.getString("UserName", "sa");
            password = sharedPreferences.getString("Password", "123");
            loadDataRecyclerView();
        }
    }

    private void loadDataRecyclerView() {
        listWordBookmark.setVisibility(View.VISIBLE);
        loginAccount.setVisibility(View.GONE);
        LoadData loadDataThread = new LoadData();
        loadDataThread.execute(userName);
    }

    /**
     * Load data from SQL Server with AsyncTask
     */
    public class LoadData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            words.clear();
        }

        @Override
        protected String doInBackground(String... params) {
            words.clear();
            words.addAll(WordFavorite.getListWordBookMark(params[0],password));
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
